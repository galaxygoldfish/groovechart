package com.groovechart.app.android.network

import android.app.Activity
import com.groovechart.app.android.consts.Credentials
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse

class SpotifyAuthService {

    fun promptUserLogin(activityContext: Activity) {
        AuthorizationClient.openLoginActivity(
            activityContext,
            1337,
            AuthorizationRequest.Builder(
                Credentials.CLIENT_ID,
                AuthorizationResponse.Type.TOKEN,
                "groovechart://login"
            ).setScopes(
                arrayOf(
                    "user-follow-read",
                    "user-top-read",
                    "user-read-private",
                    "user-read-email",
                    "playlist-modify-public",
                    "playlist-modify-private"
                )
            ).setShowDialog(false)
                .build()
        )
    }

}