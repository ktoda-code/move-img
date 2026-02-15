package com.ktoda.moveimg.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ktoda.moveimg.data.config.LocalAppConfig

@Composable
fun MainScreen() {
    val appConfigs = LocalAppConfig.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(
                RoundedCornerShape(
                    bottomStart = appConfigs.radius,
                    bottomEnd = appConfigs.radius
                )
            )
            .background(appConfigs.frameBgClr)
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Main Screen Content", color = appConfigs.textClr)
            }
        }
    }
}
