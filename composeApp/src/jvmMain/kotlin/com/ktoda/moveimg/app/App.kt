package com.ktoda.moveimg.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope
import com.ktoda.moveimg.ui.components.TitleBar
import com.ktoda.moveimg.ui.screens.MainScreen

@Composable
fun WindowScope.App() {
    val radius = 14.dp

    val frameShape = RoundedCornerShape(radius)
    val borderColor = Color(0x26FFFFFF)
    val frameBg = Color(0xFF0E0F12)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clip(frameShape)
            .background(frameBg)
            .border(width = 1.dp, color = borderColor, shape = frameShape)
            .padding(1.dp)
    ) {
        Column(Modifier.fillMaxSize()) {
            TitleBar()
            MainScreen()
        }
    }
}