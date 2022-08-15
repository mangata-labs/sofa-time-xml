package com.mangata.tvshow_data.remote.dto.tvShowDetailResponse

import kotlinx.serialization.Serializable

@Serializable
internal data class SpokenLanguageDto(
    val english_name: String,
    val iso_639_1: String,
    val name: String,
)