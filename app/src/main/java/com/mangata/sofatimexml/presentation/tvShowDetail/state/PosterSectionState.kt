package com.mangata.sofatimexml.presentation.tvShowDetail.state

import com.mangata.tvshow_domain.model.image.Poster
import com.mangata.tvshow_domain.model.video.Video

data class PosterSectionState(
    val trailer: Video? = null,
    val posters: List<Poster> = emptyList()
)