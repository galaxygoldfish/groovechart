package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val display_name: String,
    val email: String,
    val id: String,
    val images: List<Image>,
    val followers: Followers
)