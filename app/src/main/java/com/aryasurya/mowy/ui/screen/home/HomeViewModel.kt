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

    private val _moviesUpcomingState = MutableStateFlow<UiState<List<ResultsItem>>>(UiState.Loading)
    val moviesRatedState = _moviesUpcomingState.asStateFlow()

    private val _moviesNowPlayingState = MutableStateFlow<UiState<List<ResultsItem>>>(UiState.Loading)
    val moviesNowPlayingState = _moviesNowPlayingState.asStateFlow()

    init {
        fetchMovies()
        fetchUpcomingMovies()

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

    private fun fetchUpcomingMovies() {
        viewModelScope.launch {
            try {
                val movieResponse = movieRepository.fetchUpcomingMovies()
                _moviesUpcomingState.value = UiState.Success(movieResponse.results)
            } catch (e: Exception) {
                _moviesUpcomingState.value = UiState.Error("Failed to fetch movies")
            }
        }
    }

    private fun detailMovie(movieId: String) {
        viewModelScope.launch {
            try {
                val movieResponse = movieRepository.detailMovie(movieId)
                _moviesNowPlayingState.value = UiState.Success(movieResponse.results)
            } catch (e: Exception) {
                _moviesNowPlayingState.value = UiState.Error("Failed to fetch movies")
            }
        }
    }
}