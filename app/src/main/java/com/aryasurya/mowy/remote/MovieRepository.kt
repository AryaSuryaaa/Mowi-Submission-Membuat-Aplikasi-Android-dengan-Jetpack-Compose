package com.aryasurya.mowy.remote

import androidx.compose.runtime.MutableState
import com.aryasurya.mowy.local.entity.WatchlistMovie
import com.aryasurya.mowy.local.room.WatchlistDao
import com.aryasurya.mowy.remote.data.ApiService
import com.aryasurya.mowy.remote.response.DetailMovie
import com.aryasurya.mowy.remote.response.MovieResponse
import com.aryasurya.mowy.remote.response.SearchMovieResponse
import com.aryasurya.mowy.remote.response.VideoResultsItem
import com.aryasurya.mowy.remote.response.VideoYoutube

class MovieRepository(
    private val apiService: ApiService ,
    private val watchlistDao: WatchlistDao
) {
    suspend fun fetchPopularMovies(): MovieResponse {
        return apiService.getPopularMovie()
    }

    suspend fun fetchUpcomingMovies(): MovieResponse {
        return apiService.getTopRatedMovie()
    }

    suspend fun detailMovie(movieId: Int): DetailMovie {
        return apiService.getDetailMovie(movieId)
    }

    private suspend fun videoMovie(movieId: Int): VideoYoutube {
        return apiService.getVideoMovie(movieId)
    }

    suspend fun getOfficialVideo(movieId: Int): VideoResultsItem? {
        val videoYoutube = videoMovie(movieId)

        // Menggunakan filter untuk mendapatkan data dengan type == "Trailer"
        return videoYoutube.results.find { it.type == "Trailer" }
    }

    suspend fun searchMovie(keyword: String, adult: Boolean = false): SearchMovieResponse {
        return apiService.searchMovie(keyword, adult)
    }


    suspend fun getAllWatchlistMovies(): List<WatchlistMovie> {
        return watchlistDao.getAllWatchlistMovies()
    }

    suspend fun insertWatchlistMovie(movie: WatchlistMovie) {
        watchlistDao.insertWatchlistMovie(movie)
    }

    suspend fun deleteWatchlistMovie(movie: WatchlistMovie) {
        watchlistDao.deleteWatchlistMovie(movie)
    }


    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            apiService: ApiService ,
            watchlistDao: WatchlistDao
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(apiService, watchlistDao)
            }.also { instance = it }
    }
}