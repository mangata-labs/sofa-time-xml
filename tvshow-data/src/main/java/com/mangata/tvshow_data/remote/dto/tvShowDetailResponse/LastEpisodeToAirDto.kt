package com.mangata.tvshow_data.remote.dto.tvShowDetailResponse

import kotlinx.serialization.Serializable

@Serializable
internal data class LastEpisodeToAirDto(
    val air_date: String,
    val episode_number: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val production_code: String,
    val season_number: Int,
    val still_path: String? = null,
    val vote_average: Double,
    val vote_count: Int
)