package me.weishu.kernelsu.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/** DirtySU's new concept surface: deep black, neon signal accents, mono HUD labels. */
object HackerTheme {
    val Void = Color(0xFF05060A)
    val Panel = Color(0xFF0C0F18)
    val PanelRaised = Color(0xFF121624)
    val Line = Color(0xFF252B42)
    val Text = Color(0xFFE8ECF7)
    val TextDim = Color(0xFF98A2BC)
    val Violet = Color(0xFFB388FF)
    val Cyan = Color(0xFF4DE3E1)
    val Lime = Color(0xFF9BFF6B)

    fun recolor(base: ColorScheme, accent: Color): ColorScheme = base.copy(
        primary = accent,
        onPrimary = Void,
        primaryContainer = accent.copy(alpha = 0.16f),
        onPrimaryContainer = accent,
        inversePrimary = accent,
        secondary = Cyan,
        onSecondary = Void,
        secondaryContainer = Cyan.copy(alpha = 0.14f),
        onSecondaryContainer = Cyan,
        tertiary = Lime,
        onTertiary = Void,
        tertiaryContainer = Lime.copy(alpha = 0.12f),
        onTertiaryContainer = Lime,
        background = Void,
        onBackground = Text,
        surface = Panel,
        onSurface = Text,
        surfaceVariant = PanelRaised,
        onSurfaceVariant = TextDim,
        surfaceTint = accent,
        inverseSurface = Text,
        inverseOnSurface = Void,
        error = Color(0xFFFF6B7A),
        onError = Void,
        errorContainer = Color(0xFFFF6B7A).copy(alpha = 0.14f),
        onErrorContainer = Color(0xFFFFB3BE),
        outline = Line,
        outlineVariant = Line.copy(alpha = 0.55f),
        scrim = Color(0xFF000000),
    )

    val Typography: Typography = androidx.compose.material3.Typography().copy(
        displayLarge = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
            letterSpacing = (-1).sp,
        ),
        headlineLarge = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            letterSpacing = (-0.5).sp,
        ),
        headlineMedium = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            letterSpacing = 0.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.4.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            letterSpacing = 0.8.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            letterSpacing = 0.6.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
    )
}
