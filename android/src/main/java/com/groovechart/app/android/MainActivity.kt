package com.groovechart.app.android

import android.app.ComponentCaller
import android.content.Intent
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
import com.groovechart.app.android.network.SpotifyAuthService
import com.groovechart.app.android.view.AuthenticationFailView
import com.groovechart.app.android.view.home.HomeView
import com.groovechart.app.android.view.settings.HomepageRearrangeView
import com.groovechart.app.android.view.OnboardingView
import com.groovechart.app.android.view.settings.SettingsView
import com.spotify.sdk.android.auth.AuthorizationClient
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
        // If the authentication token is expired or missing, launch the auth activity
        if (mmkv.decodeLong(PreferenceKey.AUTH_EXPIRY_UNIX) <= System.currentTimeMillis()
            && mmkv.decodeBool(PreferenceKey.ONBOARDING_COMPLETE)) {
            SpotifyAuthService().launchUserAuthFlow(this)
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
            composable(NavDestinationKey.Settings) {
                SettingsView(navigationController)
            }
            composable(NavDestinationKey.HomepageRearrange) {
                HomepageRearrangeView(navigationController)
            }
        }
    }

    /**
     * This currently handles receiving the authentication token from the Spotify SDK
     */
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
        caller: ComponentCaller
    ) {
        super.onActivityResult(requestCode, resultCode, data, caller)
        val spotifyAuthResponse = AuthorizationClient.getResponse(resultCode, data)
        // If we receive a valid response, save and move on to the home screen
        spotifyAuthResponse.accessToken?.let { token ->
            MMKV.defaultMMKV().apply {
                encode(PreferenceKey.AUTH_TOKEN, token)
                encode(PreferenceKey.ONBOARDING_COMPLETE, true)
                encode(PreferenceKey.AUTH_EXPIRY_UNIX, System.currentTimeMillis() + 3600000)
            }
            navigationController.navigate(NavDestinationKey.Home)
        }
    }

}
