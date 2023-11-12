package com.aryasurya.mowy.ui.screen.search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.mowy.remote.MovieRepository
import com.aryasurya.mowy.remote.response.ResultsItemMovie
import com.aryasurya.mowy.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel(val repository: MovieRepository): ViewModel() {
    // LiveData untuk hasil pencarian film
    private val _searchResults = MutableStateFlow<UiState<List<ResultsItemMovie>>>(UiState.Loading)
    val searchResults = _searchResults.asStateFlow()
//
//    private val _query = mutableStateOf("")
//    val query: State<String> get() = _query

    fun searchMovies(keyword: String, adult: Boolean = false) {
        viewModelScope.launch {
//            _query.value = keyword
            try {
                val response = repository.searchMovie(keyword, adult)
                _searchResults.value = UiState.Success(response.results)
            } catch (e: Exception) {
                _searchResults.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }

    // Fungsi untuk memfilter hasil pencarian berdasarkan keyword
//    fun filterMoviesByKeyword(keyword: String) {
//        val currentResults = searchResults.value ?: emptyList()
//
//        // Menggunakan filter untuk mendapatkan data dengan title mengandung keyword
//        val filteredResults = currentResults.filter { it.title.contains(keyword, ignoreCase = true) }
//        _searchResults.value = filteredResults
//    }
}