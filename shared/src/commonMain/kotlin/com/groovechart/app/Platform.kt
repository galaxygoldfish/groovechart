package com.groovechart.app

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform