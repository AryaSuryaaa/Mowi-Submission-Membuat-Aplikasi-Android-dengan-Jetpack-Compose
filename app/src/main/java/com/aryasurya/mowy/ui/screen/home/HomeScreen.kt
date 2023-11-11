package com.aryasurya.mowy.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aryasurya.mowy.di.Injection
import com.aryasurya.mowy.remote.response.ResultsItem
import com.aryasurya.mowy.ui.ViewModelFactory
import com.aryasurya.mowy.ui.common.UiState
import com.aryasurya.mowy.ui.components.Banner
import com.aryasurya.mowy.ui.components.HomeHeader
import com.aryasurya.mowy.ui.components.MovieItem
import com.aryasurya.mowy.ui.components.MovieItemDisplay
import com.aryasurya.mowy.ui.components.SectionText

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier.fillMaxSize() ,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())) ,
    navigateToDetail: (Long) -> Unit ,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        val popularMoviesState by viewModel.moviesState.collectAsState(initial = UiState.Loading)
        val topRatedMoviesState by viewModel.moviesRatedState.collectAsState(initial = UiState.Loading)

        when (topRatedMoviesState) {
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                HomeHeader(
                    movieList = (topRatedMoviesState as UiState.Success<List<ResultsItem>>).data.random(),
                )
                SectionText(text = "Top Rated")
                TopRatedMovieList(
                    movieList = (topRatedMoviesState as UiState.Success<List<ResultsItem>>).data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )

            }
            is UiState.Error -> {

            }
        }

        SectionText(text = "Popular")

        when (popularMoviesState) {
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                PopularMovieList(
                    movieList = (popularMoviesState as UiState.Success<List<ResultsItem>>).data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun TopRatedMovieList(
    movieList: List<ResultsItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .testTag("RewardList")
            .height(260.dp)
    ) {
        items(movieList, key = {it.id}) { data ->
            MovieItemDisplay(image = "https://image.tmdb.org/t/p/original" + data.posterPath, modifier = Modifier.clickable {
                navigateToDetail(data.id.toLong())
            })
        }
    }
}

@Composable
fun PopularMovieList(
    movieList: List<ResultsItem>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit
) {
    LazyHorizontalGrid(
        rows = GridCells.Adaptive(160.dp) ,
        contentPadding = PaddingValues(16.dp, 8.dp) ,
        horizontalArrangement = Arrangement.spacedBy(4.dp) ,
        modifier = modifier
            .testTag("RewardList")
            .height(260.dp)
    ) {
        items(movieList, key = {it.id}) { data ->
            MovieItemDisplay(image = "https://image.tmdb.org/t/p/original" + data.posterPath, modifier = Modifier.clickable {
                navigateToDetail(data.id.toLong())
            })
        }
    }
}




