package com.groovechart.app.android.viewmodel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.network.SpotifyAuthService
import com.groovechart.app.model.Artist
import com.groovechart.app.model.Song
import com.groovechart.app.model.TopItems
import com.groovechart.app.networking.SpotifyNetworkService
import com.groovechart.app.networking.consts.TimeRange
import com.tencent.mmkv.MMKV

class TopItemListViewModel : ViewModel() {

    var topTrackList by mutableStateOf(listOf<Song>())
    var topArtistList by mutableStateOf(listOf<Artist>())

    var loadingItems by mutableStateOf(true)
    var selectedTimeRange by mutableStateOf(TimeRange.SHORT_TERM)

    val networkService = SpotifyNetworkService()
    val mmkv = MMKV.defaultMMKV()

    /**
     * Update the list of top tracks in accordance with a given time range
     * @param timeRange A TimeRange specifying the range to get top tracks from
     * @param context An activity context used if reauthentication is needed
     */
    suspend fun fetchTracks(timeRange: String, context: Activity) {
        loadingItems = true
        networkService.fetchTopTracks(
            mmkv.decodeString(PreferenceKey.AUTH_TOKEN) ?: "",
            onSuccess = {
                topTrackList = it.items
            },
            onFailure = {
                Log.e("D", "failure with code $it")
            },
            onReauthRequired = { url, authToken ->
                Log.e("D", "reauth required")
                SpotifyAuthService().launchUserAuthFlow(context)
            },
            limit = 50,
            timeRange = timeRange
        )
        loadingItems = false
    }

    /**
     * Update the list of top artists in accordance with a given time range
     * @param timeRange A TimeRange specifying the range to get top artists from
     * @param context An activity context used if reauthentication is needed
     */
    suspend fun fetchArtists(timeRange: String, context: Activity) {
        loadingItems = true
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
                SpotifyAuthService().launchUserAuthFlow(context)
            },
            limit = 50,
            timeRange = timeRange
        )
        loadingItems = false
    }

}