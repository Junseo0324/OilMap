package com.devhjs.oilmap.presentation.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FavoriteScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateBack: () -> Unit = {}
) {
    val favoriteState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is FavoriteEvent.NavigateToStationDetail -> {
                    onNavigateToDetail(event.stationId)
                }
                is FavoriteEvent.NavigateBack -> {
                    onNavigateBack()
                }
            }
        }
    }

    FavoriteScreen(
        state = favoriteState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}