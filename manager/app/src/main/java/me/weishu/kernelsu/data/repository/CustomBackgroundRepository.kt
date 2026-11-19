package me.weishu.kernelsu.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.core.content.edit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.weishu.kernelsu.ksuApp
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Stores the user's custom background image in app-private storage and exposes
 * the decoded bitmap plus a dominant-colour seed for the theme engine.
 */
object CustomBackgroundRepository {

    private const val FILE_NAME = "custom_background.jpg"
    private const val MAX_DIMENSION = 2048
    private const val JPEG_QUALITY = 88

    private val prefs by lazy {
        ksuApp.getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    private val settings: SettingsRepository = SettingsRepositoryImpl()

    private val file: File
        get() = File(ksuApp.filesDir, FILE_NAME)

    fun hasBackground(): Boolean = settings.customBackgroundEnabled && file.exists()

    fun loadBitmap(): Bitmap? {
        if (!hasBackground()) return null
        return runCatching {
            val bounds = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeFile(file.absolutePath, bounds)
            val scale = maxOf(
                1,
                kotlin.math.ceil(bounds.outWidth / MAX_DIMENSION.toFloat()).toInt(),
                kotlin.math.ceil(bounds.outHeight / MAX_DIMENSION.toFloat()).toInt(),
            )
            BitmapFactory.decodeFile(
                file.absolutePath,
                BitmapFactory.Options().apply { inSampleSize = scale },
            )
        }.getOrNull()
    }

    suspend fun apply(uri: Uri): Boolean = withContext(Dispatchers.IO) {
        runCatching {
            val source = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val decoder = ImageDecoder.createSource(ksuApp.contentResolver, uri)
                ImageDecoder.decodeBitmap(decoder) { decoder, imageInfo, _ ->
                    val imageSize = imageInfo.size
                    val scale = maxOf(1, maxOf(imageSize.width, imageSize.height) / MAX_DIMENSION)
                    if (scale > 1) decoder.setTargetSize(imageSize.width / scale, imageSize.height / scale)
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                    decoder.isMutableRequired = false
                }
            } else {
                @Suppress("DEPRECATION")
                ksuApp.contentResolver.openInputStream(uri)?.use {
                    BitmapFactory.decodeStream(it)
                } ?: error("empty input stream")
            }

            val stream = ByteArrayOutputStream()
            source.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, stream)
            file.writeBytes(stream.toByteArray())

            settings.customBackgroundEnabled = true
            settings.backgroundVersion += 1
            true
        }.getOrDefault(false)
    }

    fun clear() {
        if (file.exists()) file.delete()
        prefs.edit { putBoolean("custom_background_enabled", false) }
        settings.customBackgroundEnabled = false
        settings.backgroundVersion += 1
    }

    /** Average colour of the image, used as a MaterialKolor seed when enabled. */
    fun dominantColor(): Int {
        val bitmap = loadBitmap() ?: return 0
        return runCatching {
            val small = Bitmap.createScaledBitmap(bitmap, 1, 1, true)
            val color = small.getPixel(0, 0)
            if (small !== bitmap) small.recycle()
            color
        }.getOrDefault(0)
    }
}
