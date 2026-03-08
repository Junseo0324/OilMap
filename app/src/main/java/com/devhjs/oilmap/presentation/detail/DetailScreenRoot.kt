package com.devhjs.oilmap.presentation.detail

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailScreenRoot(
    viewModel: DetailViewModel = hiltViewModel(),
    onBack: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel.event) {
        viewModel.event.collect { event ->
            when (event) {
                is DetailEvent.NavigateBack -> onBack()
                is DetailEvent.MakeCall -> {
                    val intent = Intent(Intent.ACTION_DIAL, "tel:${event.phoneNumber}".toUri())
                    context.startActivity(intent)
                }
                is DetailEvent.OpenNavigation -> {
                    // 길안내 (카카오맵 또는 기본 지도 앱)
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        "geo:0,0?q=${Uri.encode(event.address)}".toUri()
                    )
                    context.startActivity(intent)
                }
            }
        }
    }

    DetailScreen(
        state = state,
        onAction = viewModel::onAction,
    )
}