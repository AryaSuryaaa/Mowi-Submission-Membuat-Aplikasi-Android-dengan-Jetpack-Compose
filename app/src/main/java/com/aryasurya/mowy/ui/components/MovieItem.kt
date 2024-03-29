package com.aryasurya.mowy.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aryasurya.mowy.R
import com.aryasurya.mowy.ui.theme.MowyTheme

@Composable
fun MovieItem(
    image: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = modifier.width(140.dp).background(color = Color.White),
    ) {
        Column {
            AsyncImage(
                model = image ,
                contentDescription = null ,
                contentScale = ContentScale.Crop ,
                modifier = modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Text(
                text = title ,
                maxLines = 2 ,
                overflow = TextOverflow.Ellipsis ,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(4.dp, 8.dp)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MovieItemPrev() {
//    MowyTheme {
//        MovieItem("Arya Surya")
//    }
//}