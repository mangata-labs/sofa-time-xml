package com.mangata.tvshow_data.remote.dto.imagesResponse

import kotlinx.serialization.Serializable

@Serializable
data class BackdropDto(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: String?,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
)