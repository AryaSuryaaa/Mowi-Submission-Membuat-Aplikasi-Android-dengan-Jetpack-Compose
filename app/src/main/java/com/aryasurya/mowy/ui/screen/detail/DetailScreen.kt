package com.aryasurya.mowy.ui.screen.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aryasurya.mowy.R
import com.aryasurya.mowy.di.Injection
import com.aryasurya.mowy.remote.response.DetailMovie
import com.aryasurya.mowy.remote.response.VideoResultsItem
import com.aryasurya.mowy.remote.response.VideoYoutube
import com.aryasurya.mowy.ui.ViewModelFactory
import com.aryasurya.mowy.ui.common.UiState
import com.aryasurya.mowy.ui.components.YoutubePlayer

@Composable
fun DetailScreen(
    movieId: Long ,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit
) {

    Column {
        viewModel.videoTrailer.collectAsState(initial = UiState.Loading).value.let { result ->
            when (result) {
                is UiState.Loading -> {
                    viewModel.fetchOfficialVideo(movieId.toInt())
                }

                is UiState.Success -> {
                    TrailerYoutube(
                        videoYoutube = result.data ,
                        onBackClick = navigateBack
                    )
                }

                is UiState.Error -> {}
            }
        }

        viewModel.moviesNowPlayingState.collectAsState(initial = UiState.Loading).value.let { result ->
            when (result) {
                is UiState.Loading -> {
                    viewModel.detailMovie(movieId.toInt())
                }

                is UiState.Success -> {
                    val data = result.data
                    DetailContent(
                        movieDetail = data ,
                    )
                }

                is UiState.Error -> {}
            }
        }
    }
}

@Composable
fun TrailerYoutube(
    videoYoutube: VideoResultsItem,
    onBackClick: () -> Unit ,
    modifier: Modifier = Modifier
) {
    Column {
        Icon(
            imageVector = Icons.Default.ArrowBack ,
            contentDescription = null ,
            modifier = Modifier
                .padding(16.dp)
                .clickable { onBackClick() }
        )
        YoutubePlayer(
            youtubeVideoId = videoYoutube.key ,
            lifecycleOwner = LocalLifecycleOwner.current
        )
    }
}

@Composable
fun DetailContent(
    movieDetail: DetailMovie,
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = movieDetail.title , modifier = modifier.padding(16.dp, 4.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = movieDetail.overview, modifier = modifier.padding(16.dp, 4.dp))

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .clickable(
                    interactionSource = MutableInteractionSource() ,
                    indication = rememberRipple(bounded = false , color = Color.DarkGray) ,
                    onClick = {}
                )
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_play) ,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )

            Text(
                text = "Play",
                modifier = Modifier.padding(start = 4.dp),
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(32.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color.White)
                .clickable(
                    interactionSource = MutableInteractionSource() ,
                    indication = rememberRipple(bounded = false , color = Color.DarkGray) ,
                    onClick = {}
                )
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_download) ,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Black)
            )

            Text(
                text = "Download",
                modifier = Modifier.padding(start = 4.dp),
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
            )
        }
        Row {
            Column(
                modifier = Modifier
                    .width(80.dp)
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_add) ,
                    "",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.white))
                )

                Text(
                    "My List",
                    Modifier.padding(top = 2.dp),
                    colorResource(R.color.white) ,
                    12.sp
                )
            }

            Column(
                modifier = Modifier
                    .width(80.dp)
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_thumb_up) ,
                    "",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.white))
                )

                Text(
                    "Like",
                    Modifier.padding(top = 2.dp),
                    colorResource(R.color.white) ,
                    12.sp
                )
            }

            Column(
                modifier = Modifier
                    .width(80.dp)
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_share) ,
                    "",
                    modifier = Modifier
                        .width(20.dp)
                        .height(20.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.white))
                )

                Text(
                    "Share",
                    Modifier.padding(top = 2.dp),
                    colorResource(R.color.white) ,
                    12.sp
                )
            }
        }

    }


}


//@Preview
//@Composable
//fun DetailScPrev() {
//    MowyTheme {
//        DetailContent()
//    }
//}