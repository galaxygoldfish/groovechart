package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val name: String,
    val images: List<Image>,
    val genres: List<String> = emptyList()
)
