package com.mangata.tvshow_data.remote.dto.tvShowDetailResponse

import kotlinx.serialization.Serializable

@Serializable
internal data class CreatedByDto(
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val profile_path: String? = null
)