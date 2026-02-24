package com.devhjs.oilmap.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.devhjs.oilmap.core.navigation.MainBottomNavigationBar

@Composable
fun MainScreenRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            MainBottomNavigationBar(navController = navController)
        }
    ) { innerPadding ->
        MainScreen(
            modifier = Modifier.padding(innerPadding),
            navController = navController
        )
    }
}