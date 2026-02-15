package com.ktoda.moveimg.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowScope
import androidx.compose.ui.window.WindowState
import com.ktoda.moveimg.data.config.LocalAppConfig

@Composable
fun WindowScope.TitleBar(
    windowState: WindowState,
    onClose: () -> Unit
) {
    val appConfigs = LocalAppConfig.current
    // Check placement state once
    val isMaximized = windowState.placement == WindowPlacement.Maximized

    // Define shape: Square if maximized, Rounded top if floating
    val titleBarShape = if (isMaximized) {
        RectangleShape // Square when maximized
    } else {
        RoundedCornerShape(
            topStart = appConfigs.radius,
            topEnd = appConfigs.radius
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(titleBarShape)
            .background(appConfigs.frameBgClr)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //ensures dragging the empty space moves the window, but not dragging the buttons
        WindowDraggableArea(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "MoveImg",
                    color = appConfigs.textClr,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )

                Spacer(Modifier.weight(1f))
            }
        }

        // Minimize (-)
        WindowButton(onClick = { windowState.isMinimized = true }) { color ->
            Canvas(Modifier.fillMaxSize()) {
                drawLine(
                    color = color,
                    start = Offset(0f, size.height / 2),
                    end = Offset(size.width, size.height / 2),
                    strokeWidth = 1.5f
                )
            }
        }

        // Maximize / Restore (Dynamic Icon)
        WindowButton(
            onClick = {
                windowState.placement = if (isMaximized) {
                    WindowPlacement.Floating
                } else {
                    WindowPlacement.Maximized
                }
            }
        ) { iconColor ->
            Canvas(Modifier.fillMaxSize()) {
                val strokeWidth = 1.5f
                val cornerRadius = CornerRadius(2.dp.toPx())

                if (isMaximized) {
                    // Restore Icon (Overlapping squares)
                    val iconSize = size.width * 0.70f
                    val offsetDist = size.width * 0.30f

                    // Back square (outline)
                    drawRoundRect(
                        color = iconColor,
                        topLeft = Offset(offsetDist, 0f),
                        size = Size(iconSize, iconSize),
                        cornerRadius = cornerRadius,
                        style = Stroke(width = strokeWidth)
                    )
                    // The Mask (Solid Eraser)
                    drawRoundRect(
                        color = appConfigs.panelBgClr,
                        topLeft = Offset(0f, offsetDist),
                        size = Size(iconSize, iconSize),
                        cornerRadius = cornerRadius
                    )
                    // Front square (outline)
                    drawRoundRect(
                        color = iconColor,
                        topLeft = Offset(0f, offsetDist),
                        size = Size(iconSize, iconSize),
                        cornerRadius = cornerRadius,
                        style = Stroke(width = strokeWidth)
                    )
                } else {
                    // Maximize Icon (Single square)
                    drawRoundRect(
                        color = iconColor,
                        topLeft = Offset.Zero,
                        size = size,
                        cornerRadius = cornerRadius,
                        style = Stroke(width = strokeWidth)
                    )
                }
            }
        }

        // Close (X) - Pass the isMaximized state!
        WindowButton(
            onClick = onClose,
            isClose = true,
            isMaximized = isMaximized // <--- Important!
        ) { color ->
            Canvas(Modifier.fillMaxSize()) {
                drawLine(color = color,
                    start = Offset.Zero,
                    end = Offset(size.width,size.height),
                    strokeWidth = 1.5f
                )
                drawLine(
                    color = color,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, 0f),
                    strokeWidth = 1.5f
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun WindowButton(
    onClick: () -> Unit,
    isClose: Boolean = false,
    isMaximized: Boolean = false,
    content: @Composable (iconColor: Color) -> Unit
) {
    val appConfigs = LocalAppConfig.current
    var isHovered by remember { mutableStateOf(false) }

    // Define shape for the button itself
    val buttonShape = if (isClose && !isMaximized) {
        // Only round the top-right if floating and it's the close button
        RoundedCornerShape(topEnd = appConfigs.radius)
    } else {
        // Square otherwise
        RectangleShape
    }

    val currentBg = when {
        isClose && isHovered -> Color(0xFFE81123)
        isHovered -> appConfigs.textClr.copy(alpha = 0.10f)
        else -> Color.Transparent
    }

    val iconColor = if (isClose && isHovered) Color.White else appConfigs.textClr

    Box(
        modifier = Modifier
            .width(46.dp)
            .fillMaxHeight()
            .clip(buttonShape) // Apply button shape
            .background(currentBg)
            .clickable(onClick = onClick)
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false },
        contentAlignment = Alignment.Center
    ) {
        Box(Modifier.size(10.dp)) {
            content(iconColor)
        }
    }
}