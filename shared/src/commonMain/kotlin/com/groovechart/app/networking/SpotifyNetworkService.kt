package com.groovechart.app.networking

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
        onFailure: () -> Unit
    ) {
        makeRequest<User>(
            url = "https://api.spotify.com/v1/me",
            authToken = authToken,
            onSuccess = {
                onSuccess(it)
            },
            onFailure = { code ->
                onFailure()
            },
            client,
            jsonSerializer
        )
    }

}