package com.aryasurya.mowy.ui.components.section

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.aryasurya.mowy.ui.components.MovieItemDisplay
import com.aryasurya.mowy.ui.components.SectionText
import com.aryasurya.mowy.ui.theme.MowyTheme

@Composable
fun HomeSection(
    modifier: Modifier = Modifier
) {
    Column {
        SectionText(text = "Now Showing")
        MovieItemDisplay()
    }
}

@Preview
@Composable
fun HomeSectionPrev() {
    MowyTheme {
        HomeSection()
    }
}