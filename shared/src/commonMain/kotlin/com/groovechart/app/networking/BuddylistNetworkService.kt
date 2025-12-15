package com.groovechart.app.networking

import com.groovechart.app.model.User
import com.groovechart.app.model.buddylist.BuddylistActivity
import com.groovechart.app.networking.consts.BuddylistEndpoints
import com.groovechart.app.networking.consts.SpotifyEndpoints
import io.ktor.client.HttpClient
import kotlinx.serialization.json.Json

class BuddylistNetworkService {

    private val client = HttpClient()
    private val jsonSerializer = Json { ignoreUnknownKeys = true }

    suspend fun fetchFriendActivity(
        authToken: String,
        onSuccess: (BuddylistActivity) -> Unit,
        onFailure: (Int) -> Unit,
        onReauthRequired: (url: String, authToken: String) -> Unit
    ) {
        makeRequest<BuddylistActivity>(
            url = BuddylistEndpoints.FRIEND_ACTIVITY,
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



}