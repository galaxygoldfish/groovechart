package com.groovechart.app.model.buddylist

import kotlinx.serialization.Serializable

@Serializable
data class BuddylistTrack(
    val name: String,
    val imageUrl: String,
    val album: BuddylistData,
    val artist: BuddylistData
)