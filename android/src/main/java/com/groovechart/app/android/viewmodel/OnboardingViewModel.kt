package com.groovechart.app.android.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.network.SpotifyAuthService

class OnboardingViewModel : ViewModel() {

    /**
     * Authenticate the user using the Spotify App.
     * @param activityContext - The current context
     */
    fun launchUserAuthFlow(activityContext: Activity) {
        SpotifyAuthService().launchUserAuthFlow(activityContext)
    }

}