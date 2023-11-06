package com.aryasurya.mowy.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.mowy.remote.MovieRepository
import com.aryasurya.mowy.remote.response.ResultsItem
import com.aryasurya.mowy.ui.common.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    private val _moviesState = MutableStateFlow<UiState<List<ResultsItem>>>(UiState.Loading)
    val moviesState = _moviesState.asStateFlow()

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch {
            try {
                val movieResponse = movieRepository.fetchPopularMovies()
                _moviesState.value = UiState.Success(movieResponse.results)
            } catch (e: Exception) {
                _moviesState.value = UiState.Error("Failed to fetch movies")
            }
        }
    }
}