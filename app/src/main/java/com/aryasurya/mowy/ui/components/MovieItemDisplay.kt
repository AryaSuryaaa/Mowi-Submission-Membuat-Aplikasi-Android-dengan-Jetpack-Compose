package com.aryasurya.mowy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aryasurya.mowy.R
import com.aryasurya.mowy.ui.theme.MowyTheme

@Composable
fun MovieItemDisplay(
    image: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = image ,
        contentDescription = null ,
        contentScale = ContentScale.Crop ,
        modifier = modifier
            .width(180.dp)
            .height(240.dp)
            .clip(RoundedCornerShape(16.dp))

    )
}

//@Preview(showBackground = true)
//@Composable
//fun MovieItemDisplayPrev() {
//    MowyTheme {
//        MovieItemDisplay()
//    }
//}