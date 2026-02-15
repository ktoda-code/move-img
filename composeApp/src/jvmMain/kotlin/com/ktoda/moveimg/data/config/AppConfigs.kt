package com.ktoda.moveimg.data.config

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
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
    val accentClr: Color,
    val radius: Dp = 8.dp,
    val frameShape: RoundedCornerShape = RoundedCornerShape(radius)
)

// --- DEFAULTS ---
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

val LocalAppConfig = staticCompositionLocalOf<AppConfigs> {
    error("No AppConfigs provided")
}

@Composable
fun MoveImgTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 1. Fetch Windows Accent Color (Safe JNA Call)
    // We use 'remember' so we don't hit the Registry on every frame.
    val windowsAccent = remember { WindowsThemeHelper.getSystemAccentColor() }

    // 2. Select and build the config
    // We 'remember' this result so we don't allocate a new AppConfigs object
    // unless the theme or accent color explicitly changes.
    val appConfig = remember(isDarkTheme, windowsAccent) {
        val basePalette = if (isDarkTheme) DarkPalette else LightPalette
        basePalette.copy(accentClr = windowsAccent)
    }

    CompositionLocalProvider(LocalAppConfig provides appConfig) {
        content()
    }
}

/**
 * Helper object to interact with Windows Registry
 */
private object WindowsThemeHelper {
    fun getSystemAccentColor(): Color {
        return try {
            // Check OS before calling JNA to prevent crashes on non-Windows dev machines
            val os = System.getProperty("os.name").lowercase()
            if (!os.contains("win")) return DefaultAccent

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
            // Fallback if registry access fails
            DefaultAccent
        }
    }
}