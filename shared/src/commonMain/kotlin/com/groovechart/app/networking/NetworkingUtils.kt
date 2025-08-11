package com.groovechart.app.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

suspend inline fun <reified T> makeRequest(
    url: String,
    authToken: String,
    onSuccess: (response: T) -> Unit,
    onFailure: (errorCode: Int) -> Unit,
    client: HttpClient,
    jsonSerializer: Json,
    requestType: Int = RequestType.Spotify
) {
    val response = client.get(url) {
        header("Authorization", "Bearer $authToken")
    }
    when (response.status.value) {
        200 -> {
            try {
                onSuccess(jsonSerializer.decodeFromString<T>(response.bodyAsText()))
            } catch (_: Exception) {
                onFailure(200)
            }
        }
        401 -> {
            if (requestType == RequestType.Spotify) {
                // Reauthenticate
            } else {
                onFailure(401)
            }
        }
        else -> onFailure(response.status.value)
    }
}