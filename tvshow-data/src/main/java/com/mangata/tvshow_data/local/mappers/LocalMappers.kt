package com.mangata.tvshow_data.local.mappers

import com.mangata.tvshow_data.local.dao.tvShow.TrackedTvShow
import com.mangata.tvshow_domain.model.tvShowList.TvShow

internal fun TrackedTvShow.toTvShow() : TvShow {
    return TvShow(
        id = id,
        name = name,
        overview = description,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage
    )
}

internal fun TvShow.toTrackedTvShow() : TrackedTvShow {
    return TrackedTvShow(
        id = id,
        name = name,
        description = overview,
        posterPath = posterPath,
        backdropPath = backdropPath,
        voteAverage = voteAverage
    )
}