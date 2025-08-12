package com.groovechart.app.networking

import com.groovechart.app.model.Artist
import com.groovechart.app.model.Genres
import com.groovechart.app.model.TopItems
import com.groovechart.app.model.User
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class SpotifyNetworkService {

    private val client = HttpClient()
    private val jsonSerializer = Json { ignoreUnknownKeys = true }

    suspend fun fetchUserDetails(
        authToken: String,
        onSuccess: (User) -> Unit,
        onFailure: (Int) -> Unit,
        onReauthRequired: (url: String, authToken: String) -> Unit
    ) {
        makeRequest<User>(
            url = SpotifyEndpoints.USER_DETAILS,
            authToken = authToken,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = { code ->
                onFailure(code)
            },
            onReauthRequired = { url, authToken ->
                onReauthRequired(url, authToken)
            },
            client,
            jsonSerializer
        )
    }

    suspend fun fetchTopArtists(
        authToken: String,
        onSuccess: (TopItems<Artist>) -> Unit,
        onFailure: (Int) -> Unit,
        onReauthRequired: (url: String, authToken: String) -> Unit
    ) {
        makeRequest<TopItems<Artist>>(
            url = SpotifyEndpoints.USER_TOP_ARTISTS,
            authToken = authToken,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = { code ->
                onFailure(code)
            },
            onReauthRequired = { url, authToken ->
                onReauthRequired(url, authToken)
            },
            client,
            jsonSerializer
        )
    }

    suspend fun fetchTopGenres(
        authToken: String,
        onSuccess: (List<String>) -> Unit,
        onFailure: (Int) -> Unit,
        onReauthRequired: (url: String, authToken: String) -> Unit
    ) {
        makeRequest<TopItems<Artist>>(
            url = SpotifyEndpoints.USER_TOP_ARTISTS,
            authToken = authToken,
            onSuccess = { response ->
                val totalGenreList = mutableListOf<String>()
                val topArtistList = response.items
                topArtistList.forEach { artist ->
                    totalGenreList.addAll(artist.genres!!)
                }
                onSuccess(findTopSixFrequentItems(totalGenreList))
            },
            onFailure = { code ->
                onFailure(code)
            },
            onReauthRequired = { url, authToken ->
                onReauthRequired(url, authToken)
            },
            client,
            jsonSerializer
        )
    }

}