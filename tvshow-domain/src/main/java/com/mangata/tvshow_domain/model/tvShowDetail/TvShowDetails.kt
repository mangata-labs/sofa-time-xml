package com.mangata.tvshow_domain.model.tvShowDetail

import com.mangata.tvshow_domain.model.tvShowList.TvShow
import java.util.*

data class TvShowDetails(
    val id: Int,
    val name: String,
    val genres: List<Genre>,
    val number_of_episodes: Int,
    val number_of_seasons: Int,
    val overview: String,
    val backdropPath: String,
    val posterPath: String,
    val lastAiredDate: Date?,
    val firstAirDate: Date?,
    val seasons: List<Season>,
    val episode_run_time: List<Int>,
    val homepage: String,
    val inProduction: Boolean,
    val networks: List<Network>,
    val voteAverage: Double,
    val voteCount: Int,
    val status: String,
)

fun TvShowDetails.toTvShow() : TvShow {
    return TvShow(
        id = id,
        name = name,
        overview = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage
    )
}

