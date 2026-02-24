package com.devhjs.oilmap.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DetailScreenRoot(
    modifier: Modifier = Modifier,
    stationId: String
) {
    DetailScreen(stationId = stationId)
}