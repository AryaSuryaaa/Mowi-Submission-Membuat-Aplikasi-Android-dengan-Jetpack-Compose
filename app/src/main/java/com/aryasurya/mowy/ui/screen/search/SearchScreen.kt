package com.aryasurya.mowy.ui.screen.search

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aryasurya.mowy.di.Injection
import com.aryasurya.mowy.remote.response.ResultsItem
import com.aryasurya.mowy.remote.response.ResultsItemMovie
import com.aryasurya.mowy.ui.ViewModelFactory
import com.aryasurya.mowy.ui.common.UiState
import com.aryasurya.mowy.ui.components.HomeHeader
import com.aryasurya.mowy.ui.components.MovieItem
import com.aryasurya.mowy.ui.components.MovieItemDisplay
import com.aryasurya.mowy.ui.components.SectionText
import com.aryasurya.mowy.ui.screen.detail.TrailerYoutube
import com.aryasurya.mowy.ui.screen.home.HomeViewModel
import com.aryasurya.mowy.ui.screen.home.TopRatedMovieList
import com.aryasurya.mowy.ui.theme.MowyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    viewModel: SearchViewModel = viewModel(factory = ViewModelFactory(Injection.provideRepository())),
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit ,
) {
    var text by remember {
        mutableStateOf("")
    }
    var active by remember {
        mutableStateOf(false)
    }
    var items = remember {
        mutableStateListOf(text)
    }

    LaunchedEffect(text) {
        viewModel.searchMovies(text)
    }

    Column(modifier = modifier.fillMaxWidth()) {
        if (!active) {
            Icon(
                imageVector = Icons.Default.ArrowBack ,
                contentDescription = null ,
                modifier = Modifier
                    .padding(16.dp)
                    .clickable { navigateBack() }
            )
        }

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
                items.add(text)
                active = false
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(text = "Search Movie")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
            },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (text.isNotEmpty()) {
                                text = ""
                            } else {
                                active = false
                            }
                        },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
        ) {
            items.forEach {
                Row(
                    modifier = Modifier.padding(all = 14.dp)
                ) {
                    Icon(
                        modifier = Modifier.padding(end = 10.dp),
                        imageVector = Icons.Default.History,
                        contentDescription = "History Icon"
                    )
                    Text(text = it)
                }
            }
        }

        viewModel.searchResults.collectAsState(initial = UiState.Loading).value.let { result ->
            when (result) {
                is UiState.Loading -> {
                }

                is UiState.Success -> {
                    ContentSearch(movieList = result.data, navigateToDetail = navigateToDetail)
                }

                is UiState.Error -> {}
            }
        }

    }
//            searchResults.forEach{ (initial, heroes) ->
//                stickyHeader {
//                    CharacterHeader(char = initial)
//                }
//                items(heroes, key = { it.id }) { hero ->
//                    HeroListItem(
//                        name = hero.name ,
//                        photoUrl = hero.photoUrl,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .animateItemPlacement(tween(durationMillis = 100))
//                    )
//                }
//            }
}

@Composable
fun ContentSearch(
    movieList: List<ResultsItemMovie>,
    modifier: Modifier = Modifier.fillMaxSize(),
    navigateToDetail: (Long) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp, 8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .testTag("RewardList")
            .height(260.dp)
    ) {
        items(movieList, key = {it.id}) { data ->
            MovieItem(image = "https://image.tmdb.org/t/p/original" + data.posterPath, title = data.title, modifier = Modifier.clickable {
                navigateToDetail(data.id.toLong())
            })
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SearchPrev() {
//    MowyTheme {
//        SearchScreen(navigateBack = {})
//    }
//}