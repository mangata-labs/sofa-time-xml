package com.mangata.tvshow_data.local.dao.tvShow

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tv_shows_table")
    suspend fun getAllTrackedTvShows(): List<TrackedTvShow>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWatchList(tvShow: TrackedTvShow)

    @Query("DELETE FROM tv_shows_table WHERE id = :id")
    suspend fun deleteTrackedTvShow(id: Int)
}