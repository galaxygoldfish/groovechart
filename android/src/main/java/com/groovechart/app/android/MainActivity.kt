package com.groovechart.app.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.groovechart.app.android.consts.NavDestinationKey
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.view.OnboardingView
import com.tencent.mmkv.MMKV

class MainActivity : ComponentActivity() {

    lateinit var navigationController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat
            .getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true
        setContent {
            NavigationHost()
        }
    }

    @Composable
    fun NavigationHost() {
        val mmkv = MMKV.defaultMMKV()
        navigationController = rememberNavController()
        NavHost(
            navController = navigationController,
            startDestination = if (mmkv.decodeBool(PreferenceKey.ONBOARDING_COMPLETE)) {
                NavDestinationKey.Home
            } else {
                NavDestinationKey.Onboarding
            },
            modifier = Modifier.fillMaxSize()
        ) {
            composable(NavDestinationKey.Onboarding) {
                OnboardingView()
            }
            composable(NavDestinationKey.Home) {

            }
        }
    }

}
