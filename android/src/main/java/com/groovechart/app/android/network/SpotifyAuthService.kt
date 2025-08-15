package com.groovechart.app.android.network

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import com.groovechart.app.android.consts.Credentials
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

/**
 * This class handles user authentication using the Spotify Android SDK
 */
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

    /**
     * Authenticate the user using the Spotify App. This will open the app to
     * receive the user's authentication token. Can be used for reauthentication.
     * @param activityContext The current context (activity)
     */
    fun launchUserAuthFlow(activityContext: Activity) {
        val builder = AuthorizationRequest.Builder(
            Credentials.CLIENT_ID,
            AuthorizationResponse.Type.TOKEN,
            REDIRECT_URI
        ).setScopes(SCOPES)
            .setShowDialog(true)
            .build()
        AuthorizationClient.openLoginActivity(
            activityContext,
            REQUEST_CODE,
            builder
        )
    }

}