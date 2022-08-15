package com.mangata.core.images

import com.mangata.core.images.TmdbImageSizes.backdropSizes
import com.mangata.core.images.TmdbImageSizes.baseImageUrl
import com.mangata.core.images.TmdbImageSizes.logoSizes
import com.mangata.core.images.TmdbImageSizes.posterSizes

object ImageUrlManager {

    fun getPosterUrl(path: String?, sizeType: SizeType = SizeType.MEDIUM): String? {
        return if (path.isNullOrEmpty()) null
        else "$baseImageUrl${selectSize(posterSizes, sizeType)}$path"
    }

    fun getBackdropUrl(path: String?, sizeType: SizeType = SizeType.MEDIUM): String? {
        return if (path.isNullOrEmpty()) null
        else "$baseImageUrl${selectSize(backdropSizes, sizeType)}$path"
    }

    fun getLogoUrl(path: String?, sizeType: SizeType = SizeType.MEDIUM): String? {
        return if (path.isNullOrEmpty()) null
        else "$baseImageUrl${selectSize(logoSizes, sizeType)}$path"
    }

    private fun selectSize(sizes: List<String>, sizeType: SizeType) : String {
        return when(sizeType) {
            SizeType.SMALL -> sizes[0]
            SizeType.MEDIUM -> sizes[1]
            SizeType.BIG -> sizes[2]
            SizeType.ORIGINAL -> sizes[3]
        }
    }
}