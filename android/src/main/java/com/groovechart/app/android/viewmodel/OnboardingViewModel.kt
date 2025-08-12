package com.groovechart.app.android.viewmodel

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.consts.Credentials
import com.groovechart.app.android.network.SpotifyAuthService
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class OnboardingViewModel : ViewModel() {

    fun launchUserAuthFlow(activityContext: Activity) {
        SpotifyAuthService().launchUserAuthFlow(activityContext)
    }

}