package com.mangata.tvshow_data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mangata.tvshow_data.local.dao.tvShow.TvShowDao
import com.mangata.tvshow_data.local.dao.tvShow.TrackedTvShow

@Database(entities = [TrackedTvShow::class], version = 1, exportSchema = false)
abstract class SofaTimeDatabase : RoomDatabase() {

    abstract fun tvShowDao(): TvShowDao

    companion object {
        const val DATABASE_TV_SHOW_TABLE = "tv_shows_table"
        const val DATABASE_NAME = "sofa_time_database"
    }
}