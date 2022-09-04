package com.mangata.tvshow_presentation.tvShowDetail.components.headerSection

import com.mangata.core.extensions.toYear
import com.mangata.tvshow_domain.model.tvShowDetail.Genre
import com.mangata.tvshow_domain.model.tvShowDetail.TvShowDetails

data class TvDetailsHeaderModel(
    val title: String = "",
    val genres: List<Genre> = emptyList(),
    val runTime: Int? = null,
    val startYear: Int? = null,
    val lastAiredYear: Int? = null,
    val numberOfSeasons: Int = 0,
    val score: Double = 0.0,
    val inProduction: Boolean = false,
) {
    fun displayDate(): String {
        return if (startYear == null) "Unknown"
        else if (inProduction) "$startYear - Present"
        else "$startYear - $lastAiredYear"
    }

    fun displayGenres() : String {
        val genreNames = genres.map { it.name }
        return if (genreNames.size == 1) {
            genreNames.first()
        } else {
            var displayString = ""
            for (i in genreNames.indices) {
                displayString +=
                    if (i == genreNames.indices.last) genreNames[i]
                    else genreNames[i] + ", "
            }
            return displayString
        }
    }
}

fun TvShowDetails.toDetailHeaderModel(): TvDetailsHeaderModel {
    return TvDetailsHeaderModel(
        title = name,
        genres = genres,
        runTime = episode_run_time.firstOrNull(),
        lastAiredYear = lastAiredDate?.toYear(),
        startYear = firstAirDate?.toYear(),
        numberOfSeasons = number_of_seasons,
        score = voteAverage,
        inProduction = inProduction
    )
}