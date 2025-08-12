package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Genres(
    val genres: List<String>
)