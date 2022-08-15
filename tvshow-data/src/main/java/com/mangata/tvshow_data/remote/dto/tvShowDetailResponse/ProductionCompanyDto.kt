package com.mangata.tvshow_data.remote.dto.tvShowDetailResponse

import kotlinx.serialization.Serializable

@Serializable
internal data class ProductionCompanyDto(
    val id: Int,
    val logo_path: String? = null,
    val name: String,
    val origin_country: String,
)