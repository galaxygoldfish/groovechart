package com.groovechart.app.android.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.network.SpotifyAuthService

class OnboardingViewModel : ViewModel() {

    fun launchUserAuthFlow(activityContext: Activity) {
        val spotifyAuthService = SpotifyAuthService()
        spotifyAuthService.launchUserAuthFlow(activityContext)
    }


}