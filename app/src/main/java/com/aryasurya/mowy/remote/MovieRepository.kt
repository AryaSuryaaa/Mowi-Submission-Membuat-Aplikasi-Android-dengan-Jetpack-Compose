package com.aryasurya.mowy.remote

import com.aryasurya.mowy.remote.data.ApiService
import com.aryasurya.mowy.remote.response.DetailMovie
import com.aryasurya.mowy.remote.response.MovieResponse
import com.aryasurya.mowy.remote.response.ResultsItem
import com.aryasurya.mowy.remote.response.VideoResultsItem
import com.aryasurya.mowy.remote.response.VideoYoutube
import com.aryasurya.mowy.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

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