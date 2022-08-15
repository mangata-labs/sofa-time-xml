package com.mangata.tvshow_domain.model.image

data class Poster(
    val aspectRatio: Double,
    val filePath: String?,
    val height: Int,
    val width: Int,
    val voteCount: Int,
)
