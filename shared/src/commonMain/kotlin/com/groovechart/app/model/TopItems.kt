package com.groovechart.app.model

import kotlinx.serialization.Serializable

@Serializable
data class TopItems<T>(
    //val total: Int,
    val items: List<T>
)