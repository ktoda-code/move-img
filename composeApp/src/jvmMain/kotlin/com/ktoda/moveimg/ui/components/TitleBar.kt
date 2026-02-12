package com.ktoda.moveimg.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope

@Composable
fun WindowScope.TitleBar() {
    val panelClr = Color(0xFF14161B)
    val textClr = Color(0xFFD0D0D0)

    WindowDraggableArea {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .background(panelClr)
                .padding(horizontal = 12.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 14.dp
                    )
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("MoveImg", color = textClr)

            Spacer(Modifier.weight(1f))

            Text("—", color = textClr, modifier = Modifier.padding(8.dp))
            Text("□", color = textClr, modifier = Modifier.padding(8.dp))
            Text("✕", color = textClr, modifier = Modifier.padding(8.dp))
        }
    }
}