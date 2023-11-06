package com.aryasurya.mowy.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aryasurya.mowy.di.Injection
import com.aryasurya.mowy.remote.response.ResultsItem
import com.aryasurya.mowy.ui.ViewModelFactory
import com.aryasurya.mowy.ui.common.UiState
import com.aryasurya.mowy.ui.components.MovieItem
import com.aryasurya.mowy.ui.components.MovieItemDisplay
import com.aryasurya.mowy.ui.components.SectionText

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier ,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())) ,
//    navigateToDetail: (Long) -> Unit ,
) {
    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        val popularMoviesState by viewModel.moviesState.collectAsState(initial = UiState.Loading)

        SectionText(text = "Popular Movie")

        when (popularMoviesState) {
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                HomeContent(
                    movieList = (popularMoviesState as UiState.Success<List<ResultsItem>>).data,
                    modifier = modifier
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun HomeContent(
    movieList: List<ResultsItem> ,
    modifier: Modifier ,
//    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp) ,
        contentPadding = PaddingValues(16.dp) ,
        horizontalArrangement = Arrangement.spacedBy(16.dp) ,
        verticalArrangement = Arrangement.spacedBy(16.dp) ,
        modifier = modifier.testTag("RewardList")
    ) {
        items(movieList) { data ->
            MovieItem(image = "https://image.tmdb.org/t/p/original" + data.posterPath, title = data.title)
        }
    }
}


