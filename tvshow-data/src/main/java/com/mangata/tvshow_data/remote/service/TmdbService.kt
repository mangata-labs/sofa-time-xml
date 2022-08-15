package com.mangata.tvshow_data.remote.service

import com.mangata.tvshow_data.remote.dto.imagesResponse.ImagesDto
import com.mangata.tvshow_data.remote.dto.tvShowDetailResponse.TvShowDetailDto
import com.mangata.tvshow_data.remote.dto.tvShowResponse.TvShowResponseDto
import com.mangata.tvshow_data.remote.dto.videoResponse.TrailerDto

internal interface TmdbService {
    suspend fun getPopularTvShows(pageNumber: Int) : TvShowResponseDto
    suspend fun getTvShowDetails(id: Int) : TvShowDetailDto
    suspend fun getVideoForTvShow(id: Int) : TrailerDto
    suspend fun getImagesForTvShow(id: Int) : ImagesDto
    suspend fun searchTvShows(query: String, pageNumber: Int) : TvShowResponseDto
    suspend fun getTrendingTvShows() : TvShowResponseDto
    suspend fun getSimilarTvShows(id: Int) : TvShowResponseDto
}