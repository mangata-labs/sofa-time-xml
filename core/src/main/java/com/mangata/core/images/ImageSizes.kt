package com.mangata.core.images

internal object TmdbImageSizes {

    const val baseImageUrl = "https://image.tmdb.org/t/p/"

    val posterSizes = listOf(
        "w154",
        "w500",
        "w780",
        "original"
    )

    val backdropSizes = listOf(
        "w300",
        "w780",
        "w1280",
        "original"
    )

    val logoSizes = listOf(
        "w45",
        "w154",
        "w500",
        "original"
    )
}

enum class SizeType {
    SMALL,
    MEDIUM,
    BIG,
    ORIGINAL
}