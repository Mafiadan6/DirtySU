package me.weishu.kernelsu.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.weishu.kernelsu.R
import me.weishu.kernelsu.ui.theme.HackerTheme

/** HUD status hero — the DirtySU signature band above the root status card. */
@Composable
fun HackerHero(rootActive: Boolean, version: String?, mode: String) {
    val accent = if (rootActive) HackerTheme.Lime else HackerTheme.Violet
    val headline = if (rootActive) "ROOT ENGINE ONLINE" else "ENGINE STANDBY"
    val sub = if (rootActive) "ACCESS GRANTED // KERNEL HOOK ACTIVE" else "AWAITING KERNEL INITIALIZATION"

    Box(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .shadow(20.dp, RoundedCornerShape(20.dp), ambientColor = accent.copy(alpha = 0.35f))
            .background(
                Brush.linearGradient(
                    listOf(HackerTheme.PanelRaised, HackerTheme.Panel),
                ),
            )
            .border(1.dp, HackerTheme.Line, RoundedCornerShape(20.dp)),
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(
                    Brush.horizontalGradient(
                        listOf(Color.Transparent, accent, HackerTheme.Cyan, Color.Transparent),
                    ),
                ),
        )
        Column(Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    Modifier
                        .size(10.dp)
                        .clip(RoundedCornerShape(50))
                        .background(accent)
                        .shadow(10.dp, RoundedCornerShape(50), ambientColor = accent),
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    "DIRTYSU // ROOT CONTROL",
                    color = HackerTheme.TextDim,
                    fontFamily = FontFamily.Monospace,
                    fontSize = 11.sp,
                    letterSpacing = 2.sp,
                )
            }
            Spacer(Modifier.height(14.dp))
            Text(
                headline,
                color = Color.White,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp,
                letterSpacing = (-0.5).sp,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                sub,
                color = HackerTheme.TextDim,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                letterSpacing = 0.6.sp,
            )
            Spacer(Modifier.height(16.dp))
            Row {
                HudChip("MODE", if (mode.isBlank()) "AUTO" else mode, accent)
                Spacer(Modifier.width(8.dp))
                HudChip("KERNEL", version ?: "N/A", HackerTheme.Cyan)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                stringResource(R.string.about_dev_name),
                color = HackerTheme.Violet,
                fontFamily = FontFamily.Monospace,
                fontSize = 11.sp,
                letterSpacing = 1.4.sp,
            )
        }
    }
}

@Composable
private fun HudChip(label: String, value: String, accent: Color) {
    Column(
        Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(accent.copy(alpha = 0.10f))
            .border(1.dp, accent.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(label, color = HackerTheme.TextDim, fontFamily = FontFamily.Monospace, fontSize = 9.sp, letterSpacing = 1.sp)
        Text(value, color = accent, fontFamily = FontFamily.Monospace, fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}
