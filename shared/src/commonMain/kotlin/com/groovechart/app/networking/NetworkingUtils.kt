package com.groovechart.app.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.util.logging.KtorSimpleLogger
import io.ktor.util.logging.Logger
import kotlinx.serialization.json.Json

expect fun log(message: String)

suspend inline fun <reified T> makeRequest(
    url: String,
    authToken: String,
    onSuccess: (response: T) -> Unit,
    onFailure: (errorCode: Int) -> Unit,
    onReauthRequired: (url: String, authToken: String) -> Unit,
    client: HttpClient,
    jsonSerializer: Json,
    queryBuilder: HttpRequestBuilder.() -> Unit = {}
) {
    val response = client.get(
        urlString = url,
        block = {
            header("Authorization", "Bearer $authToken")
            queryBuilder()
        }
    )
    when (response.status.value) {
        200 -> {
            log(response.bodyAsText())
            try {
                onSuccess(jsonSerializer.decodeFromString<T>(response.bodyAsText()))
            } catch (e: Exception) {
                log(e.message.toString())
                onFailure(987) // 987 == serialization error
            }
        }
        401 -> {
            onReauthRequired(url, authToken)
        }
        else -> onFailure(response.status.value)
    }
}

fun findTopSixFrequentItems(list: List<String>): List<String> {
    return list.groupingBy { it }
        .eachCount()
        .toList()
        .sortedByDescending { (_, frequency) -> frequency }
        .take(6)
        .map { it.first }
}