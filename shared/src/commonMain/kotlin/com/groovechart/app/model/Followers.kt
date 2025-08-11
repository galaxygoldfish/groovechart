package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class Followers(
    val total: Int
)