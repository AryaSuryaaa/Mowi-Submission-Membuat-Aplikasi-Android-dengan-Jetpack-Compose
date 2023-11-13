package com.aryasurya.mowy.ui.screen.playlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.room.Room
import com.aryasurya.mowy.local.entity.WatchlistMovie
import com.aryasurya.mowy.local.room.AppDatabase
import com.aryasurya.mowy.local.room.WatchlistDao

@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel, // Sesuaikan dengan nama ViewModel Anda
    navigateToDetail: (Long) -> Unit
) {
    val watchlist by viewModel.watchlist.collectAsState()

    LazyColumn {
        items(watchlist) { movie ->
            WatchlistItem(
                movie = movie,
                onClick = { navigateToDetail(movie.) }
            )
        }
    }
}

@Composable
fun WatchlistItem(
    movie: WatchlistMovie, // Sesuaikan dengan model data Anda
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick),
//        elevation = 8.dp
    ) {
        // Tambahkan konten item watchlist di sini
        // Contoh:
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = movie.title, fontWeight = FontWeight.Bold)
            // Tambahkan detail lainnya seperti gambar, sinopsis, dll.
        }
    }
}

@Composable
fun ProvideDatabaseContent(
    content: @Composable (WatchlistDao) -> Unit
) {
    val context = LocalContext.current
    val database = remember {
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "watchlist-database"
        ).build()
    }

    content(database)
}