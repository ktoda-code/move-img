package com.ktoda.moveimg.app

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ktoda.moveimg.data.config.AppConfigs
import com.ktoda.moveimg.data.config.LocalAppConfig
import com.ktoda.moveimg.ui.screens.MainScreen

@Preview
@Composable
fun AppPreview() {
    val previewConfig = AppConfigs(
        frameBgClr = Color(0xFF202020),
        panelBgClr = Color(0xFF2B2B2B),
        textClr = Color(0xFFFFFFFF),
        borderClr = Color(0x1AFFFFFF),
        accentClr = Color(0xFF0078D4),
        radius = 8.dp
    )

    CompositionLocalProvider(LocalAppConfig provides previewConfig) {
        Column {
            MainScreen()
        }
    }
}