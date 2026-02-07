package com.ktoda.moveimg

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