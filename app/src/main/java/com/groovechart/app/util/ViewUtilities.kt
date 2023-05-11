package com.groovechart.app.util

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun SetSystemBarColors(
    colorStatus: Color,
    colorNav: Color,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    LocalView.current.apply {
        if (!isInEditMode) {
            SideEffect {
                val window = (context as Activity).window
                window.statusBarColor = colorStatus.toArgb()
                window.navigationBarColor = colorNav.toArgb()
                WindowCompat.getInsetsController(window, this).apply {
                    isAppearanceLightStatusBars = !darkTheme
                    isAppearanceLightNavigationBars = !darkTheme
                }
            }
        }
    }
}