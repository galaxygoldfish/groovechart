package com.groovechart.app.model.buddylist

import kotlinx.serialization.Serializable

@Serializable
data class BuddylistItem(
    val timestamp: Long,
    val user: BuddylistUser,
    val track: BuddylistTrack
)