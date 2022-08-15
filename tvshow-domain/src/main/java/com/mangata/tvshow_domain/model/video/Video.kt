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
    fun createUrl() : String? {
        return when(sourceSite) {
            is SourceSite.YouTube -> "https://www.youtube.com/watch?v=$key"
            is SourceSite.Unknown -> null
        }
    }
}

sealed class SourceSite {
    class YouTube(name: String) : SourceSite()
    class Unknown(name: String) : SourceSite()

    companion object {
        fun fromString(input: String) : SourceSite {
            return when(input) {
                "YouTube" -> YouTube(input)
                else -> Unknown("Unknown")
            }
        }
    }
}

sealed class VideoType(val type: String) {
    object Trailer : VideoType("Trailer")
    object Behind_The_Scenes : VideoType("Behind the Scenes")
    object Featurette : VideoType("Featurette")
    object Teaser : VideoType("Teaser")
    object Unknown : VideoType("Unknown")

    companion object {
        fun fromString(input: String) : VideoType {
            return when(input) {
                "Trailer" -> Trailer
                "Behind the Scenes" -> Behind_The_Scenes
                "Featurette" -> Featurette
                "Teaser" -> Teaser
                else -> Unknown
            }
        }
    }
}