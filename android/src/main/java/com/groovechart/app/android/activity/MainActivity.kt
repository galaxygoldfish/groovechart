package com.groovechart.app.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.groovechart.app.android.GroovechartTheme
import com.groovechart.app.android.view.OnboardingView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WindowCompat
                .getInsetsController(window, window.decorView)
                .isAppearanceLightStatusBars = true
            // to be updated with nav controller
            OnboardingView()
        }
    }
}
