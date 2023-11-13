package com.aryasurya.mowy.ui.screen.playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.mowy.local.entity.WatchlistMovie
import com.aryasurya.mowy.remote.MovieRepository
import kotlinx.coroutines.launch

class WatchListViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _watchlistMovies = MutableLiveData<List<WatchlistMovie>>()
    val watchlistMovies: LiveData<List<WatchlistMovie>> get() = _watchlistMovies

    init {
        getAllWatchlistMovies()
    }

    fun getAllWatchlistMovies() {
        viewModelScope.launch {
            _watchlistMovies.value = repository.getAllWatchlistMovies()
        }
    }

    fun insertWatchlistMovie(movie: WatchlistMovie) {
        viewModelScope.launch {
            repository.insertWatchlistMovie(movie)
            getAllWatchlistMovies()
        }
    }

    fun deleteWatchlistMovie(movie: WatchlistMovie) {
        viewModelScope.launch {
            repository.deleteWatchlistMovie(movie)
            getAllWatchlistMovies()
        }
    }
}
