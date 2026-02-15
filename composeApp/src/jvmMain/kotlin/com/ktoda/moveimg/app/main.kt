package com.ktoda.moveimg.app

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.ktoda.moveimg.data.config.LocalAppConfig
import com.ktoda.moveimg.data.config.MoveImgTheme
import com.ktoda.moveimg.ui.components.TitleBar

fun main() = application {

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
        MoveImgTheme {
            val appConfigs = LocalAppConfig.current

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = appConfigs.frameBgClr,
                shape = if (initialWindowState.placement == WindowPlacement.Maximized) {
                    androidx.compose.ui.graphics.RectangleShape
                } else {
                    appConfigs.frameShape
                }
            ) {
                Column {
                    TitleBar(
                        windowState = initialWindowState,
                        onClose = ::exitApplication
                    )

                    App()
                }
            }
        }
    }
}