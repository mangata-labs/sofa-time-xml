package com.mangata.tvshow_data.remote.dto.imagesResponse

import kotlinx.serialization.Serializable

@Serializable
data class ImagesDto(
    val backdrops: List<BackdropDto>,
    val id: Int,
    val posters: List<PosterDto>
)