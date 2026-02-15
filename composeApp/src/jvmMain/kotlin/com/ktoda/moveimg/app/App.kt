package com.ktoda.moveimg.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import com.ktoda.moveimg.data.config.LocalAppConfig
import com.ktoda.moveimg.ui.components.TitleBar
import com.ktoda.moveimg.ui.screens.MainScreen

@Composable
fun WindowScope.App() {
    val appConfigs = LocalAppConfig.current
    val frameShape = appConfigs.frameShape

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(frameShape)
            .background(appConfigs.frameBgClr)
            .border(width = 1.dp, color = appConfigs.borderClr, shape = frameShape)
            .padding(1.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            TitleBar()
            MainScreen()
        }
    }
}
