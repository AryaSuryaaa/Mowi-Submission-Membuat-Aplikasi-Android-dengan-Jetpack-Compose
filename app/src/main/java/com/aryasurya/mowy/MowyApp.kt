package com.aryasurya.mowy

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aryasurya.mowy.ui.components.Banner
import com.aryasurya.mowy.ui.components.section.HomeSection

@Composable
fun MowyApp(
    modifier: Modifier = Modifier
) {
    Column {
        Banner()
        HomeSection()
        HomeSection()
    }
}
