package org.khaki.schoolwatch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform