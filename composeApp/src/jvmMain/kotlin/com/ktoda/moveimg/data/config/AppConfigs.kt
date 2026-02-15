package com.ktoda.moveimg.data.config

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class AppConfigs(
    val frameBgClr: Color = Color(0xFF0E0F12),
    val panelBgClr: Color = Color(0xFF14161B),
    val textClr: Color = Color(0xFFD0D0D0),
    val borderClr: Color = Color(0x26FFFFFF),
    val radius: Dp = 14.dp,
    val frameShape: RoundedCornerShape = RoundedCornerShape(radius)
)

// FOR NOW: Assuming we wll not change colors, panel colors and radius.
val LocalAppConfig = staticCompositionLocalOf<AppConfigs> {
    error("No AppConfigs provided")
}

@Composable
fun ProvideAppConfig(
    appConfigs: AppConfigs,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAppConfig provides appConfigs) {
        content()
    }
}
