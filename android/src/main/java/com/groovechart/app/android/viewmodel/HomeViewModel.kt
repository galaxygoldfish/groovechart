package com.groovechart.app.android.viewmodel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.consts.Credentials
import com.groovechart.app.android.consts.PageNavigationKey
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.network.SpotifyAuthService
import com.groovechart.app.model.User
import com.groovechart.app.networking.SpotifyNetworkService
import com.spotify.sdk.android.auth.AuthorizationClient
import com.spotify.sdk.android.auth.AuthorizationRequest
import com.spotify.sdk.android.auth.AuthorizationResponse
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.delay

class HomeViewModel : ViewModel() {

    var currentUser: User? by mutableStateOf(null)
    var loadingDataComplete by mutableStateOf(false)
    var showAccountDialog by mutableStateOf(false)
    var currentPage by mutableIntStateOf(PageNavigationKey.Home)
    var topGenreList by mutableStateOf(listOf<String>())

    suspend fun fetch(activityContext: Activity) {
        val mmkv = MMKV.defaultMMKV()
        SpotifyNetworkService().fetchUserDetails(
            mmkv.decodeString(PreferenceKey.AUTH_TOKEN) ?: "",
            onSuccess = {
                Log.e("s", "success, ${it.display_name}")
                currentUser = it
            },
            onFailure = {
                Log.e("D", "failure with code $it")
            },
            onReauthRequired = { url, authToken ->
                Log.e("D", "reauth required")
                val spotifyAuthService = SpotifyAuthService()
                spotifyAuthService.launchUserAuthFlow(activityContext)
            }
        )
        SpotifyNetworkService().fetchTopGenres(
            mmkv.decodeString(PreferenceKey.AUTH_TOKEN) ?: "",
            onSuccess = {
                topGenreList = it
            },
            onFailure = {
                Log.e("D", "failure with code $it")
            },
            onReauthRequired = { url, authToken ->
                AuthorizationClient.openLoginActivity(
                    activityContext,
                    10101011,
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
                    ).build()
                )
            }
        )
        loadingDataComplete = true
    }
}