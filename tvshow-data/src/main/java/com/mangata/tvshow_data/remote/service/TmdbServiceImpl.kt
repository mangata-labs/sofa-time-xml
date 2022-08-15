package com.mangata.tvshow_data.remote.service

import com.mangata.tvshow_data.remote.dto.imagesResponse.ImagesDto
import com.mangata.tvshow_data.remote.dto.tvShowDetailResponse.TvShowDetailDto
import com.mangata.tvshow_data.remote.dto.tvShowResponse.TvShowResponseDto
import com.mangata.tvshow_data.remote.dto.videoResponse.TrailerDto
import com.mangata.tvshow_data.util.BuildConfigHelper
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class TmdbServiceImpl(private val client: HttpClient) : TmdbService {
    override suspend fun getPopularTvShows(pageNumber: Int): TvShowResponseDto {
        return client.get(Endpoint.popularTvShows()) {
            parameter("page", pageNumber)
            parameter("api_key", BuildConfigHelper.getTmdbApiKey())
        }.body()
    }

    override suspend fun getTvShowDetails(id: Int): TvShowDetailDto {
        return client.get(Endpoint.tvShowsDetails(id)) {
            parameter("api_key", BuildConfigHelper.getTmdbApiKey())
        }.body()
    }

    override suspend fun getVideoForTvShow(id: Int): TrailerDto {
        return client.get(Endpoint.tvShowTrailer(id)) {
            parameter("api_key", BuildConfigHelper.getTmdbApiKey())
        }.body()
    }

    override suspend fun getImagesForTvShow(id: Int): ImagesDto {
        return client.get(Endpoint.tvShowImages(id)){
            parameter("api_key", BuildConfigHelper.getTmdbApiKey())
        }.body()
    }

    override suspend fun searchTvShows(query: String, pageNumber: Int): TvShowResponseDto {
        return client.get(Endpoint.searchTvShows()) {
            parameter("api_key", BuildConfigHelper.getTmdbApiKey())
            parameter("include_adult", false)
            parameter("query", query)
            parameter("page", pageNumber)
        }.body()
    }

    override suspend fun getTrendingTvShows(): TvShowResponseDto {
        return client.get(Endpoint.trendingTvShows()) {
            parameter("api_key", BuildConfigHelper.getTmdbApiKey())
        }.body()
    }

    override suspend fun getSimilarTvShows(id: Int): TvShowResponseDto {
        return client.get(Endpoint.similarTvShows(id)) {
            parameter("api_key", BuildConfigHelper.getTmdbApiKey())
        }.body()
    }
}