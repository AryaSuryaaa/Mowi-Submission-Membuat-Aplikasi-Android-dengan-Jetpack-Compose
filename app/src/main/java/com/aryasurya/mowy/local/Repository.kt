package com.aryasurya.mowy.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.aryasurya.mowy.local.entity.WatchlistMovie
import com.aryasurya.mowy.local.room.MovieDatabase
import com.aryasurya.mowy.local.room.WatchlistDao
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val mMovieDao: WatchlistDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = MovieDatabase.getInstance(application)
        mMovieDao = db.movieDao()
    }

    fun getAllMovies(): LiveData<List<WatchlistMovie>> = mMovieDao.getAllMovie()

    fun insert(watchlistMovie: WatchlistMovie) {
        executorService.execute { mMovieDao.insert(watchlistMovie) }
    }

    fun delete(watchlistMovie: WatchlistMovie) {
        executorService.execute { mMovieDao.delete(watchlistMovie) }
    }

}