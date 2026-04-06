package com.devhjs.oilmap.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devhjs.oilmap.core.navigation.BottomNavItem
import com.devhjs.oilmap.core.navigation.MainBottomNavigationBar
import com.devhjs.oilmap.presentation.component.ad.BannerAdView
import com.devhjs.oilmap.presentation.designsystem.AppColors

@Composable
fun MainScreen(
    selectedRoute: String?,
    onBottomNavSelected: (BottomNavItem) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize().background(color = AppColors.Background),
        bottomBar = {
            Column(
                Modifier.background(color = AppColors.Background)
            ) {
                Text(
                    text = "데이터 출처: 공공데이터포털 (한국석유공사)",
                    fontSize = 11.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
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
