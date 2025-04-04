package com.groovechart.app.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.groovechart.app.android.view.OnboardingView

class OnboardingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat
            .getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true
        setContent {
            OnboardingView()
        }
    }
}