package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val url: String,
    val height: Int,
    val width: Int
)