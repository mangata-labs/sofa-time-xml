package com.mangata.tvshow_data.remote.service

internal object Endpoint {
    private const val TMDB_BASE_URL = "https://api.themoviedb.org/3/"

    fun popularTvShows() = TMDB_BASE_URL + "tv/popular"
    fun tvShowsDetails(id: Int) = TMDB_BASE_URL + "tv/$id"
    fun tvShowTrailer(id: Int) = TMDB_BASE_URL + "tv/$id/videos"
    fun tvShowImages(id: Int) = TMDB_BASE_URL + "tv/$id/images"
    fun searchTvShows() = TMDB_BASE_URL + "search/tv"
    fun trendingTvShows() = TMDB_BASE_URL + "trending/tv/week"
    fun similarTvShows(id: Int) = TMDB_BASE_URL + "tv/$id/similar"
}