package com.aryasurya.mowy.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aryasurya.mowy.local.entity.WatchlistMovie

@Dao
interface WatchlistDao {
    @Query("SELECT * FROM watchlist_movies")
    suspend fun getAllWatchlistMovies(): List<WatchlistMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWatchlistMovie(movie: WatchlistMovie)

    @Delete
    suspend fun deleteWatchlistMovie(movie: WatchlistMovie)
}