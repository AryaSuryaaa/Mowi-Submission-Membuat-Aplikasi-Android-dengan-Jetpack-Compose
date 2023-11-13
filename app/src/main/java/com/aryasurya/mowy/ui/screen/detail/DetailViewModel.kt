package com.aryasurya.mowy.ui.screen.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.mowy.remote.MovieRepository
import com.aryasurya.mowy.remote.response.DetailMovie
import com.aryasurya.mowy.remote.response.VideoResultsItem
import com.aryasurya.mowy.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(private val movieRepository: MovieRepository): ViewModel() {

//    private val _moviesNowPlayingState = MutableStateFlow<UiState<DetailMovie>>(UiState.Loading)
//    val moviesNowPlayingState = _moviesNowPlayingState.asStateFlow()

    private val _moviesNowPlayingState: MutableStateFlow<UiState<DetailMovie>> =
        MutableStateFlow(UiState.Loading)
    val moviesNowPlayingState: StateFlow<UiState<DetailMovie>>
        get() = _moviesNowPlayingState
    fun detailMovie(movieId: Int) {
        viewModelScope.launch {
            _moviesNowPlayingState.value = UiState.Loading
            try {
                val movie = movieRepository.detailMovie(movieId)
                Log.d("DetailMovie", "Response: $movie")
                if (movie != null) {
                    _moviesNowPlayingState.value = UiState.Success(movie)
                } else {
                    _moviesNowPlayingState.value = UiState.Error("Resep not found")
                }
            } catch (e: Exception) {
                Log.e("DetailMovie", "Error: ${e.message}")
                _moviesNowPlayingState.value = UiState.Error("Failed to fetch data")
            }
        }
    }

    private val _videoTrailer: MutableStateFlow<UiState<VideoResultsItem>> =
        MutableStateFlow(UiState.Loading)
    val videoTrailer: StateFlow<UiState<VideoResultsItem>>
        get() = _videoTrailer

    fun fetchOfficialVideo(movieId: Int) {
        viewModelScope.launch {
            _videoTrailer.value = UiState.Loading
            try {
                val officialVideo: VideoResultsItem? = movieRepository.getOfficialVideo(movieId)
                Log.d("DetailMovie", "Response: $officialVideo")
                officialVideo?.let {
                    _videoTrailer.value = UiState.Success(it)
                } ?: run {
                    _videoTrailer.value = UiState.Error("Official video not found")
                }
            } catch (e: Exception) {
                Log.e("DetailMovie", "Error: ${e.message}")
                _videoTrailer.value = UiState.Error("Failed to fetch official video data")
            }
        }
    }


}