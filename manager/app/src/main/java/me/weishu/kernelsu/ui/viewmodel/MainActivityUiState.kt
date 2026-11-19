package me.weishu.kernelsu.ui.viewmodel

import androidx.compose.runtime.Immutable
import me.weishu.kernelsu.ui.UiMode
import me.weishu.kernelsu.ui.theme.AppSettings

@Immutable
data class MainActivityUiState(
    val appSettings: AppSettings,
    val pageScale: Float,
    val enableBlur: Boolean,
    val enableFloatingBottomBar: Boolean,
    val enableFloatingBottomBarBlur: Boolean,
    val enableNavigationBadge: Boolean,
    val enableSwipeDismiss: Boolean,
    val pagerInterceptionMode: Int,
    val moduleDescriptionMaxLines: Int = 4,
    val uiMode: UiMode,
    val backgroundVersion: Int = 0,
    val backgroundEnabled: Boolean = false,
    val backgroundDim: Float = 0.25f,
    val backgroundOpacity: Float = 1f,
    val backgroundBlur: Boolean = true,
    val backgroundFit: Int = 0,
    val backgroundHighContrast: Boolean = false,
)
