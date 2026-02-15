package com.ktoda.moveimg.data.config

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sun.jna.platform.win32.Advapi32Util
import com.sun.jna.platform.win32.WinReg

@Immutable
data class AppConfigs(
    val frameBgClr: Color,
    val panelBgClr: Color,
    val textClr: Color,
    val borderClr: Color,
    val accentClr: Color, // New: Dynamic Windows Accent
    val radius: Dp = 8.dp,
    val frameShape: RoundedCornerShape = RoundedCornerShape(radius)
)

// Default / Fallback definitions
private val DefaultAccent = Color(0xFF0078D4) // Standard Windows Blue

private val DarkPalette = AppConfigs(
    frameBgClr = Color(0xFF202020),
    panelBgClr = Color(0xFF2B2B2B),
    textClr = Color(0xFFFFFFFF),
    borderClr = Color(0x1AFFFFFF),
    accentClr = DefaultAccent
)

private val LightPalette = AppConfigs(
    frameBgClr = Color(0xFFF3F3F3),
    panelBgClr = Color(0xFFFFFFFF),
    textClr = Color(0xFF1A1A1A),
    borderClr = Color(0x0F000000),
    accentClr = DefaultAccent
)

// FOR NOW: Assuming we wll not change colors, panel colors and radius.
val LocalAppConfig = staticCompositionLocalOf<AppConfigs> {
    error("No AppConfigs provided")
}

@Composable
fun ProvideAppConfig(
    appConfigs: AppConfigs,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAppConfig provides appConfigs) {
        content()
    }
}

@Composable
fun MoveImgTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 1. Pick the base palette (Light vs Dark)
    val baseConfig = if (isDarkTheme) DarkPalette else LightPalette

    // 2. Fetch the Windows Accent Color
    // We use 'remember' so we don't read the registry on every single frame/recomposition
    val windowsAccent = remember { WindowsThemeHelper.getSystemAccentColor() }

    // 3. Create the final config by copying base and overriding accent
    val finalConfig = baseConfig.copy(accentClr = windowsAccent)

    CompositionLocalProvider(LocalAppConfig provides finalConfig) {
        content()
    }
}

/**
 * Helper object to interact with Windows Registry
 */
private object WindowsThemeHelper {
    fun getSystemAccentColor(): Color {
        return try {
            val colorInt = Advapi32Util.registryGetIntValue(
                WinReg.HKEY_CURRENT_USER,
                "Software\\Microsoft\\Windows\\DWM",
                "AccentColor"
            )
            // Windows Registry stores color as ABGR (0xAABBGGRR)
            // We convert it to ARGB for Compose
            val a = (colorInt shr 24) and 0xFF
            val b = (colorInt shr 16) and 0xFF
            val g = (colorInt shr 8) and 0xFF
            val r = colorInt and 0xFF

            // Note: If alpha is 0 from registry, we force full opacity (255)
            val finalAlpha = if (a == 0) 255 else a

            Color(red = r, green = g, blue = b, alpha = finalAlpha)
        } catch (e: Exception) {
            // If we are not on Windows or registry fails, return default blue
            DefaultAccent
        }
    }
}
