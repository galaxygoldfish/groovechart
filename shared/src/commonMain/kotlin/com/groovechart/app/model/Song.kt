package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Song(
    val album: Album,
    val name: String,
    val id: String,
    val uri: String,
    val external_urls: ExternalUrls
)
