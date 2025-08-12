package com.groovechart.app.android

import android.app.ComponentCaller
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.groovechart.app.android.consts.NavDestinationKey
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.view.AuthenticationFailView
import com.groovechart.app.android.view.HomeView
import com.groovechart.app.android.view.OnboardingView
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.tencent.mmkv.MMKV

class MainActivity : ComponentActivity() {

    lateinit var navigationController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MMKV.initialize(this@MainActivity)
        val mmkv = MMKV.defaultMMKV()

        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true

        setContent {
            NavigationHost(mmkv)
        }
    }

    @Composable
    fun NavigationHost(mmkv: MMKV) {
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
                HomeView(navigationController)
            }
            // TODO: Refactor to a no internet page or similar
            composable(NavDestinationKey.AuthenticationFail) {
                AuthenticationFailView()
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        val spotifyAuthResponse = AuthorizationClient.getResponse(resultCode, data)
        spotifyAuthResponse.accessToken?.let { token ->
            MMKV.defaultMMKV().apply {
                encode(PreferenceKey.AUTH_TOKEN, token)
                encode(PreferenceKey.ONBOARDING_COMPLETE, true)
            }
            navigationController.navigate(NavDestinationKey.Home)
        }
        // TODO: Error handling when the access token is not provided in response (auth fail view)
    }

}
