package com.ktoda.moveimg.app

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.ktoda.moveimg.data.config.AppConfigs
import com.ktoda.moveimg.data.config.ProvideAppConfig

fun main() = application {
    val appConfigs = AppConfigs()

    val initialWindowState = WindowState(
        size = DpSize(
            width = 1200.dp,
            height = 800.dp
        ),
        position = WindowPosition.Aligned(Alignment.Center),
    )

    Window(
        onCloseRequest = ::exitApplication,
        state = initialWindowState,
        title = "Move Images",
        undecorated = true,
        transparent = true
    ) {
        ProvideAppConfig(appConfigs) {
            App()
        }
    }
}
