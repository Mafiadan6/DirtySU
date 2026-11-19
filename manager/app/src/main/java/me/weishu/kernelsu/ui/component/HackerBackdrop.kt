package me.weishu.kernelsu.ui.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.Composable
import me.weishu.kernelsu.ui.theme.HackerTheme

/** Ambient DirtySU shell: optional user image, faint grid, scanlines and top glow. */
@Composable
fun HackerBackdrop(
    bitmap: android.graphics.Bitmap?,
    dim: Float,
    opacity: Float,
    blur: Boolean,
    fit: Int,
    highContrast: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(modifier.fillMaxSize().background(HackerTheme.Void)) {
        if (bitmap != null) {
            val scale = when (fit) {
                1 -> ContentScale.Fit
                2 -> ContentScale.FillBounds
                else -> ContentScale.Crop
            }
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = scale,
                alpha = opacity,
                modifier = Modifier
                    .fillMaxSize()
                    .then(if (blur) Modifier.blur(18.dp) else Modifier),
            )
        }

        Canvas(Modifier.fillMaxSize()) {
            val step = 56.dp.toPx()
            val lineColor = HackerTheme.Violet.copy(alpha = 0.055f)
            var x = 0f
            while (x <= size.width) {
                drawLine(lineColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                x += step
            }
            var y = 0f
            while (y <= size.height) {
                drawLine(lineColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                y += step
            }
            val scan = HackerTheme.Cyan.copy(alpha = 0.025f)
            y = 0f
            while (y <= size.height) {
                drawLine(scan, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                y += 6.dp.toPx()
            }
        }

        if (bitmap != null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = (dim + if (highContrast) 0.16f else 0f).coerceIn(0f, 0.93f))),
            )
        }

        Box(
            Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(
                    Brush.verticalGradient(
                        listOf(HackerTheme.Violet.copy(alpha = 0.13f), Color.Transparent),
                    ),
                ),
        )
    }
}
