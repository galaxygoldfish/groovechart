package com.groovechart.app.model.buddylist

import kotlinx.serialization.Serializable

@Serializable
data class BuddylistActivity(
    val friends: List<BuddylistItem>
)