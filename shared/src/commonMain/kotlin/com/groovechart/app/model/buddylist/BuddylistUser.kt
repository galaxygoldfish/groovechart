package com.groovechart.app.model.buddylist

import kotlinx.serialization.Serializable

@Serializable
data class BuddylistUser(
    val name: String,
    val imageUrl: String
)