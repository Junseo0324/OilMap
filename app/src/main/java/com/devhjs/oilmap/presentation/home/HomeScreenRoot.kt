package com.devhjs.oilmap.presentation.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToDetail: (String) -> Unit = {},
    onNavigateToSettings: () -> Unit = {}
) {
    val homeState by viewModel.state.collectAsStateWithLifecycle()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val isGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true || 
                            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            if (isGranted) {
                viewModel.onAction(HomeAction.OnPermissionGranted)
            }
        }
    )

    LaunchedEffect(Unit) {
        locationPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is HomeEvent.NavigateToStationDetail -> {
                    onNavigateToDetail(event.stationId)
                }
                is HomeEvent.NavigateToSettings -> {
                    onNavigateToSettings()
                }
            }
        }
    }

    Homescreen(
        state = homeState,
        onAction = viewModel::onAction,
        modifier = modifier
    )
}