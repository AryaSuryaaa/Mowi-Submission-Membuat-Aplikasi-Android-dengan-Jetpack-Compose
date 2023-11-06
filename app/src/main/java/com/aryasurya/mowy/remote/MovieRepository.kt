package com.aryasurya.mowy.remote

import com.aryasurya.mowy.remote.data.ApiService
import com.aryasurya.mowy.remote.response.MovieResponse
import com.aryasurya.mowy.remote.response.ResultsItem
import com.aryasurya.mowy.ui.common.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MovieRepository(
    private val apiService: ApiService ,
) {

    private val resultItem = mutableListOf<ResultsItem>()

    suspend fun getPopularMovie(): UiState<List<ResultsItem>> {
        return try {
            val response = apiService.getPopularMovie()
            UiState.Success(response.results)
        } catch (e: Exception) {
            UiState.Error(e.message ?: "An error occurred")
        }
    }
}