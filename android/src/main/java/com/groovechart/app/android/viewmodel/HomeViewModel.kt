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
import com.groovechart.app.model.Artist
import com.groovechart.app.model.Song
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
    var topSongList by mutableStateOf(listOf<Song>())
    var topArtistList by mutableStateOf(listOf<Artist>())

    suspend fun fetch(activityContext: Activity) {
        val mmkv = MMKV.defaultMMKV()
        val networkService = SpotifyNetworkService()
        networkService.fetchUserDetails(
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
                SpotifyAuthService().launchUserAuthFlow(activityContext)
            }
        )
        networkService.fetchTopGenres(
            mmkv.decodeString(PreferenceKey.AUTH_TOKEN) ?: "",
            onSuccess = {
                topGenreList = it
            },
            onFailure = {
                Log.e("D", "failure with code $it")
            },
            onReauthRequired = { url, authToken ->
                Log.e("D", "reauth required")
                SpotifyAuthService().launchUserAuthFlow(activityContext)
            }
        )
        // limit to top 4 items
        networkService.fetchTopTracks(
            mmkv.decodeString(PreferenceKey.AUTH_TOKEN) ?: "",
            onSuccess = {
                topSongList = it.items
            },
            onFailure = {
                Log.e("D", "failure with code $it")
            },
            onReauthRequired = { url, authToken ->
                Log.e("D", "reauth required")
                SpotifyAuthService().launchUserAuthFlow(activityContext)
            }
        )
        // same limiting here
        networkService.fetchTopArtists(
            mmkv.decodeString(PreferenceKey.AUTH_TOKEN) ?: "",
            onSuccess = {
                topArtistList = it.items
            },
            onFailure = {
                Log.e("D", "failure with code $it")
            },
            onReauthRequired = { url, authToken ->
                Log.e("D", "reauth required")
                SpotifyAuthService().launchUserAuthFlow(activityContext)
            }
        )
        loadingDataComplete = true
    }
}