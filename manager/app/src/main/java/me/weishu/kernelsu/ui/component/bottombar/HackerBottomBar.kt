package me.weishu.kernelsu.ui.component.bottombar

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.weishu.kernelsu.Natives
import me.weishu.kernelsu.R
import me.weishu.kernelsu.ui.LocalMainPagerState
import me.weishu.kernelsu.ui.theme.HackerTheme

/** Neon command bar — large targets, clear selected state, no stock navigation look. */
@Composable
fun HackerBottomBar(navigationBadge: NavigationBadgeState) {
    if (!Natives.isFullFeatured()) return
    val state = LocalMainPagerState.current
    val items = listOf(
        Triple(R.string.home, Icons.Filled.Home, "HOME"),
        Triple(R.string.superuser, Icons.Filled.Shield, "ROOT"),
        Triple(R.string.module, Icons.Filled.Extension, "MODS"),
        Triple(R.string.settings, Icons.Filled.Settings, "CONF"),
    )

    Box(
        Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 14.dp, vertical = 8.dp),
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(26.dp))
                .background(HackerTheme.Panel.copy(alpha = 0.94f))
                .border(1.dp, HackerTheme.Line, RoundedCornerShape(26.dp))
                .shadow(18.dp, RoundedCornerShape(26.dp), ambientColor = HackerTheme.Violet),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEachIndexed { index, (label, icon, code) ->
                val selected = state.selectedPage == index
                val tint by animateColorAsState(
                    if (selected) HackerTheme.Violet else HackerTheme.TextDim,
                    label = "nav_tint",
                )
                Column(
                    Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(22.dp))
                        .clickable { if (!selected) state.animateToPage(index) }
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        Modifier
                            .size(30.dp, 3.dp)
                            .clip(RoundedCornerShape(3.dp))
                            .background(if (selected) HackerTheme.Violet else Color.Transparent),
                    )
                    Box(Modifier.size(4.dp))
                    val badge = badgeFor(index, navigationBadge)
                    BadgedBox(
                        badge = {
                            if (badge != null) {
                                Badge(
                                    containerColor = if (badge.tone == BadgeTone.Alert) {
                                        Color(0xFFFF6B7A)
                                    } else {
                                        HackerTheme.Cyan
                                    },
                                ) { Text(badge.count.toString(), fontSize = 9.sp) }
                            }
                        },
                    ) {
                        Icon(icon, stringResource(label), tint = tint, modifier = Modifier.size(22.dp))
                    }
                    Text(
                        code,
                        color = tint,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Clip,
                    )
                }
            }
        }
    }
}
