package com.ktoda.moveimg.app

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application

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
        App()
    }
}