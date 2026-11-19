package me.weishu.kernelsu.ui.screen.background

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.BlurOn
import androidx.compose.material.icons.rounded.Contrast
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.Palette
import androidx.compose.material.icons.rounded.PhotoSizeSelectLarge
import androidx.compose.material.icons.rounded.Restore
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFlexibleTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSliderState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.lifecycle.viewmodel.compose.viewModel
import me.weishu.kernelsu.R
import me.weishu.kernelsu.ui.component.material.SegmentedColumn
import me.weishu.kernelsu.ui.component.material.SegmentedDropdownItem
import me.weishu.kernelsu.ui.component.material.SegmentedListItem
import me.weishu.kernelsu.ui.component.material.SegmentedSwitchItem
import me.weishu.kernelsu.ui.component.material.TonalCard
import me.weishu.kernelsu.ui.component.material.TopBarBackButton
import me.weishu.kernelsu.ui.component.material.ExpressiveScaffold
import me.weishu.kernelsu.ui.component.material.expressiveTopAppBarColors
import me.weishu.kernelsu.ui.navigation3.LocalNavigator
import me.weishu.kernelsu.ui.viewmodel.SettingsViewModel

/** Custom background settings — pick an image and tune the ambient shell. */
@Composable
fun BackgroundScreen() {
    val navigator = LocalNavigator.current
    val context = LocalContext.current
    val viewModel = viewModel<SettingsViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val pickImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            viewModel.applyBackground(uri) { ok ->
                Toast.makeText(
                    context,
                    if (ok) R.string.background_set_success else R.string.background_pick_failed,
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    ExpressiveScaffold(
        topBar = {
            LargeFlexibleTopAppBar(
                navigationIcon = { TopBarBackButton(onClick = dropUnlessResumed { navigator.pop() }) },
                title = { Text(stringResource(R.string.background_title)) },
                colors = expressiveTopAppBarColors(),
                windowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal),
                scrollBehavior = scrollBehavior,
            )
        },
        contentWindowInsets = WindowInsets.safeDrawing.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal),
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            verticalArrangement = Arrangement.spacedBy(13.dp),
        ) {
            item {
                TonalCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                    ) {
                        Icon(
                            Icons.Rounded.Image,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Text(
                            text = stringResource(R.string.settings_background_summary),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(
                            text = if (uiState.customBackgroundEnabled) {
                                stringResource(R.string.background_set_success)
                            } else {
                                stringResource(R.string.settings_background_summary)
                            },
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
            }

            item {
                SegmentedColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    content = listOf(
                        {
                            SegmentedListItem(
                                onClick = { pickImage.launch("image/*") },
                                leadingContent = {
                                    Icon(
                                        Icons.Rounded.PhotoSizeSelectLarge,
                                        contentDescription = null,
                                    )
                                },
                                headlineContent = {
                                    Text(stringResource(R.string.background_pick_image))
                                },
                            )
                        },
                        {
                            SegmentedListItem(
                                enabled = uiState.customBackgroundEnabled,
                                onClick = {
                                    viewModel.removeBackground()
                                    Toast.makeText(
                                        context,
                                        R.string.background_removed,
                                        Toast.LENGTH_SHORT,
                                    ).show()
                                },
                                leadingContent = {
                                    Icon(Icons.Rounded.Restore, contentDescription = null)
                                },
                                headlineContent = {
                                    Text(stringResource(R.string.background_remove))
                                },
                            )
                        },
                    ),
                )
            }

            item {
                TonalCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                    val dimState = rememberSliderState(
                        value = uiState.backgroundDim,
                        trackRange = 0f..0.85f,
                    )
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        SliderLabel(
                            icon = Icons.Rounded.Contrast,
                            label = stringResource(R.string.background_dim),
                            value = "${(dimState.value * 100).toInt()}%",
                        )
                        Slider(
                            state = dimState,
                            onValueChangeFinished = { viewModel.setBackgroundDim(dimState.value) },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }

            item {
                TonalCard(modifier = Modifier.padding(horizontal = 16.dp)) {
                    val opacityState = rememberSliderState(
                        value = uiState.backgroundOpacity,
                        trackRange = 0.15f..1f,
                    )
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        SliderLabel(
                            icon = Icons.Rounded.Image,
                            label = stringResource(R.string.background_opacity),
                            value = "${(opacityState.value * 100).toInt()}%",
                        )
                        Slider(
                            state = opacityState,
                            onValueChangeFinished = { viewModel.setBackgroundOpacity(opacityState.value) },
                            modifier = Modifier.fillMaxWidth(),
                        )
                    }
                }
            }

            item {
                SegmentedColumn(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    content = listOf(
                        {
                            SegmentedDropdownItem(
                                icon = Icons.Rounded.PhotoSizeSelectLarge,
                                title = stringResource(R.string.background_fit),
                                items = listOf(
                                    stringResource(R.string.background_fit_crop),
                                    stringResource(R.string.background_fit_fit),
                                    stringResource(R.string.background_fit_stretch),
                                ),
                                selectedIndex = uiState.backgroundFit.coerceIn(0, 2),
                                onItemSelected = viewModel::setBackgroundFit,
                            )
                        },
                        {
                            SegmentedSwitchItem(
                                icon = Icons.Rounded.BlurOn,
                                title = stringResource(R.string.background_blur),
                                summary = stringResource(R.string.background_blur_summary),
                                checked = uiState.backgroundBlur,
                                onCheckedChange = viewModel::setBackgroundBlur,
                            )
                        },
                        {
                            SegmentedSwitchItem(
                                icon = Icons.Rounded.Palette,
                                title = stringResource(R.string.background_seed_color),
                                summary = stringResource(R.string.background_seed_color_summary),
                                checked = uiState.backgroundSeedColor,
                                onCheckedChange = viewModel::setBackgroundSeedColor,
                            )
                        },
                        {
                            SegmentedSwitchItem(
                                icon = Icons.Rounded.Contrast,
                                title = stringResource(R.string.background_high_contrast),
                                summary = stringResource(R.string.background_high_contrast_summary),
                                checked = uiState.backgroundHighContrast,
                                onCheckedChange = viewModel::setBackgroundHighContrast,
                            )
                        },
                    ),
                )
            }

            item { Spacer(Modifier.height(12.dp)) }
        }
    }
}

@Composable
private fun SliderLabel(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
        )
        Text(text = value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
