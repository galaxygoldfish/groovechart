package com.groovechart.app.networking.consts

object SpotifyEndpoints {
    const val BASE_URL = "https://api.spotify.com/v1"
    const val USER_TOP_ARTISTS = "$BASE_URL/me/top/artists"
    const val USER_DETAILS = "$BASE_URL/me"
    const val USER_TOP_SONGS = "$BASE_URL/me/top/tracks"
}