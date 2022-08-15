package com.mangata.tvshow_data.util

import java.lang.reflect.Field

private const val appPackageName = "com.mangata.sofatime"

internal object BuildConfigHelper {

    fun isInDebug(): Boolean {
        val isDebug: Any? = getBuildConfigValue("DEBUG")
        return if(isDebug is Boolean) isDebug
        else false
    }

    fun getTmdbApiKey() : String {
        val tmdbApiKey = getBuildConfigValue("TMDB_API_KEY")
        return if (tmdbApiKey is String) tmdbApiKey
        else ""
    }

    fun getTraktClientID() : String {
        val traktClientID = getBuildConfigValue("TRAKT_CLIENT_ID")
        return if(traktClientID is String) traktClientID
        else ""
    }

    private fun getBuildConfigValue(fieldName: String): Any? {
        val configClass = Class.forName("$appPackageName.BuildConfig")
        val field: Field = configClass.getField(fieldName)
        return field.get(null)
    }
}