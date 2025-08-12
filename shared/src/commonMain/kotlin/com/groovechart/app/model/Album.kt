package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Album(
    val artists: List<Artist>,
    val images: List<Image>,
    val id: String,
    val name: String
)