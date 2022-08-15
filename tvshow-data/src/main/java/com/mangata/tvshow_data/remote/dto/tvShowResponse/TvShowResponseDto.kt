package com.mangata.tvshow_data.remote.dto.tvShowResponse

import kotlinx.serialization.Serializable

@Serializable
internal data class TvShowResponseDto(
    val page: Int,
    val results: List<TvShowDto>,
    val total_pages: Int,
    val total_results: Int
)

@Serializable
internal data class TvShowDto(
    val id: Int,
    val name: String,
    val genre_ids: List<Int>,
    val backdrop_path: String? = null,
    val poster_path: String? = null,
    val first_air_date: String? = null,
    val origin_country: List<String>,
    val original_language: String? = null,
    val original_name: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val vote_average: Double? = null,
    val vote_count: Int? = null
)
