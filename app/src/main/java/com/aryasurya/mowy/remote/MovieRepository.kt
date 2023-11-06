package com.aryasurya.mowy.remote

import com.aryasurya.mowy.remote.data.ApiService
import com.aryasurya.mowy.remote.response.MovieResponse
import com.aryasurya.mowy.remote.response.ResultsItem
import com.aryasurya.mowy.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn

class MovieRepository(
    private val apiService: ApiService ,
) {
    suspend fun fetchPopularMovies(): MovieResponse {
        return apiService.getPopularMovie()
    }

    suspend fun fetchUpcomingMovies(): MovieResponse {
        return apiService.getTopRatedMovie()
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