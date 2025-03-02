package com.bojan.multiplicationpractise

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform