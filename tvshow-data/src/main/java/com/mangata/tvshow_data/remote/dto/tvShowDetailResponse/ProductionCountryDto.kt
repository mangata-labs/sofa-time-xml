package com.mangata.tvshow_data.remote.dto.tvShowDetailResponse

import kotlinx.serialization.Serializable

@Serializable
internal data class ProductionCountryDto(
    val iso_3166_1: String,
    val name: String
)