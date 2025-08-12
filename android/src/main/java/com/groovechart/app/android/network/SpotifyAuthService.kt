package com.groovechart.app.android.network

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.groovechart.app.android.consts.Credentials
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class SpotifyAuthService {

    private val REDIRECT_URI = "groovechart://login"
    private val REQUEST_CODE = 10000
    private val SCOPES =  arrayOf(
        "user-follow-read",
        "user-top-read",
        "user-read-private",
        "user-read-email",
        "playlist-modify-public",
        "playlist-modify-private"
    )

    fun launchUserAuthFlow(activityContext: Activity) {
        Log.e("GROOVECHART", "open login activity")
        val builder = AuthorizationRequest.Builder(
            Credentials.CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        ).setScopes(SCOPES)
            .setShowDialog(true)
            .build()
        AuthorizationClient.openLoginActivity(activityContext, REQUEST_CODE, builder)
    }

}