package com.aryasurya.mowy.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "watchlist_movies")
@Parcelize
data class WatchlistMovie(
    @PrimaryKey val movieId: Long,
    val title: String,
    val img: String,
): Parcelable
