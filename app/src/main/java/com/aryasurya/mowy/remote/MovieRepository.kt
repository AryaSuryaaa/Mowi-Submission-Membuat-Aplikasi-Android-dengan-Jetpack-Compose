package com.aryasurya.mowy.remote

import androidx.compose.runtime.MutableState
import com.aryasurya.mowy.remote.data.ApiService
import com.aryasurya.mowy.remote.response.DetailMovie
import com.aryasurya.mowy.remote.response.MovieResponse
import com.aryasurya.mowy.remote.response.SearchMovieResponse
import com.aryasurya.mowy.remote.response.VideoResultsItem
import com.aryasurya.mowy.remote.response.VideoYoutube

class MovieRepository(
    private val apiService: ApiService ,
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




    companion object {
        @Volatile
        private var instance: MovieRepository? = null
        fun getInstance(
            apiService: ApiService ,
        ): MovieRepository =
            instance ?: synchronized(this) {
                instance ?: MovieRepository(apiService)
            }.also { instance = it }
    }
}