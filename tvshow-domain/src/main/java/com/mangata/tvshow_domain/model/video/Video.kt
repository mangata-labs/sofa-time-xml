package com.mangata.tvshow_domain.model.video

data class Video(
    val id: Int,
    val title: String,
    val sourceSite: SourceSite,
    val official: Boolean,
    val videoQuality: Int,
    val key: String,
    val videoType: VideoType,
) {
    fun createUrl(): String? {
        return when (sourceSite) {
            is SourceSite.YouTube -> "https://www.youtube.com/watch?v=$key"
            is SourceSite.Unknown -> null
        }
    }
}

sealed class SourceSite {
    object YouTube : SourceSite()
    object Unknown : SourceSite()

    companion object {
        fun fromString(input: String): SourceSite {
            return when (input) {
                "YouTube" -> YouTube
                else -> Unknown
            }
        }
    }
}

sealed class VideoType {
    object Trailer : VideoType()
    object BehindTheScenes : VideoType()
    object Featured : VideoType()
    object Teaser : VideoType()
    object Unknown : VideoType()

    companion object {
        fun fromString(input: String): VideoType {
            return when (input) {
                "Trailer" -> Trailer
                "Behind the Scenes" -> BehindTheScenes
                "Featurette" -> Featured
                "Teaser" -> Teaser
                else -> Unknown
            }
        }
    }
}