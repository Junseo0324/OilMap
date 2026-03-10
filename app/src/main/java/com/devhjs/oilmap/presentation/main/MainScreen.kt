package com.devhjs.oilmap.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.devhjs.oilmap.core.navigation.BottomNavItem
import com.devhjs.oilmap.core.navigation.MainBottomNavigationBar
import com.devhjs.oilmap.presentation.component.ad.BannerAdView
import androidx.compose.foundation.layout.Column

@Composable
fun MainScreen(
    selectedRoute: String?,
    onBottomNavSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            Column {
                BannerAdView()
                MainBottomNavigationBar(
                    selectedRoute = selectedRoute,
                    onNavigate = onBottomNavSelected
                )
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}
