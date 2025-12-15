package com.groovechart.app.android.viewmodel

import android.app.Activity
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.groovechart.app.android.R
import com.groovechart.app.android.consts.PageNavigationKey
import com.groovechart.app.android.consts.PreferenceKey
import com.groovechart.app.android.network.SpotifyAuthService
import com.groovechart.app.model.Artist
import com.groovechart.app.model.Song
import com.groovechart.app.model.User
import com.groovechart.app.networking.SpotifyNetworkService
import com.groovechart.app.networking.consts.TimeRange
import com.tencent.mmkv.MMKV
import java.util.Locale

class HomeViewModel : ViewModel() {

    val mmkv = MMKV.defaultMMKV()
    var currentUser: User? by mutableStateOf(null)
    var loadingDataComplete by mutableStateOf(false)
    var showAccountDialog by mutableStateOf(false)
    var currentPage by mutableIntStateOf(PageNavigationKey.Home)
    var topGenreList by mutableStateOf(listOf<String>())
    var topSongList by mutableStateOf(listOf<Song>())
    var topArtistList by mutableStateOf(listOf<Artist>())
    var arrangementOrder: List<String> by mutableStateOf(emptyList())

    /**
     * Get the most updated home page item arrangement order from MMKV.
     */
    fun fetchArrangementOrder(activityContext: Activity) {
        val topTracks = activityContext.getString(R.string.settings_homepage_rearrange_tracks)
        val topArtists = activityContext.getString(R.string.settings_homepage_rearrange_artists)
        val topGenres = activityContext.getString(R.string.settings_homepage_rearrange_genres)
        val orderDecoded = mmkv.decodeString(
            PreferenceKey.PREFERENCE_ARRANGEMENT_ORDER,
            listOf(topGenres, topTracks, topArtists).toString()
        )!!
        arrangementOrder = orderDecoded.substring(1, orderDecoded.length - 1).split(",")
    }

    /**
     * The main request function, gets top artists, tracks, genres from Spotify API
     * @param activityContext - The current context
     */
    suspend fun fetch(activityContext: Activity) {
        loadingDataComplete = false
        val networkService = SpotifyNetworkService()
        fetchArrangementOrder(activityContext)
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
            },
            numGenres = mmkv.decodeInt(PreferenceKey.PREFERENCE_GENRE_AMOUNT, 5),
            timeRange = convertReadableToAPITime(
                readable = mmkv.decodeString(PreferenceKey.PREFERENCE_GENRE_TIME, TimeRange.SHORT_TERM)!!,
                activityContext = activityContext
            )
        )
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
            },
            limit = mmkv.decodeInt(PreferenceKey.PREFERENCE_TRACK_AMOUNT, 4),
            timeRange = convertReadableToAPITime(
                readable = mmkv.decodeString(PreferenceKey.PREFERENCE_TRACK_TIME, TimeRange.SHORT_TERM)!!,
                activityContext = activityContext
            )
        )
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
            },
            limit = mmkv.decodeInt(PreferenceKey.PREFERENCE_ARTIST_AMOUNT, 4),
            timeRange = convertReadableToAPITime(
                readable = mmkv.decodeString(PreferenceKey.PREFERENCE_ARTIST_TIME, TimeRange.SHORT_TERM)!!,
                activityContext = activityContext
            )
        )
        loadingDataComplete = true
    }

    /**
     * Converts the readable (month, year, etc) to API-readable time formats (short_term, medium_term, etc.)
     * @param readable - The readable time format
     * @param activityContext - The current context
     * @return - The API-readable time format
     */
    private fun convertReadableToAPITime(readable: String, activityContext: Activity) : String {
        return when (readable) {
            activityContext.getString(R.string.settings_timescale_short) -> TimeRange.SHORT_TERM
            activityContext.getString(R.string.settings_timescale_medium) -> TimeRange.MEDIUM_TERM
            activityContext.getString(R.string.settings_timescale_long) -> TimeRange.LONG_TERM
            else -> TimeRange.SHORT_TERM
        }
    }

}