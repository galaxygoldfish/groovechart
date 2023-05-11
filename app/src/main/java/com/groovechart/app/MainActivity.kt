package com.groovechart.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.groovechart.app.theme.GrooveChartTheme
import com.groovechart.app.view.OnboardingView
import com.tencent.mmkv.MMKV

class MainActivity : ComponentActivity() {

    private lateinit var navigationController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MMKV.initialize(this)
        setContent {
            GrooveChartTheme {
                NavHost()
            }
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    fun NavHost() {
        navigationController = rememberAnimatedNavController()
        AnimatedNavHost(
            navController = navigationController,
            startDestination = if (MMKV.defaultMMKV().decodeBool("ONBOARDING_COMPLETE")) {
                NavDestination.Home
            } else {
                NavDestination.Onboarding
            }
        ) {
            composable(NavDestination.Onboarding) {
                OnboardingView(navigationController)
            }
        }
    }

}
