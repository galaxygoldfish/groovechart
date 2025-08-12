package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val name: String,
    val images: List<Image>? = null,
    val genres: List<String> = emptyList(),
    val followers: Followers? = null
)
