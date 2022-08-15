package com.mangata.tvshow_data.remote.dto.tvShowDetailResponse

import kotlinx.serialization.Serializable

@Serializable
internal data class SeasonDto(
    val air_date: String? = null,
    val episode_count: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val poster_path: String? = null,
    val season_number: Int
)