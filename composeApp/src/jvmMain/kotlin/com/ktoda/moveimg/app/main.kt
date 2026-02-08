package com.ktoda.moveimg.app

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "moveimg",
    ) {
        App()
    }
}