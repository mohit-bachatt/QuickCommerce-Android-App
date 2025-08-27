package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.components.CustomBottomNavigation
import com.example.peeko_android_app.ui.screens.ProfileScreen
import com.example.peeko_android_app.ui.screens.SearchScreen
import com.example.peeko_android_app.ui.theme.TextPrimary

@Composable
fun HomeScreen(
    navigationViewModel: NavigationViewModel
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CustomBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { tabIndex ->
                    selectedTab = tabIndex
                    // Handle navigation based on tab selection
                    when (tabIndex) {
                        0 -> { /* Already on Home */ }
                        1 -> { /* Navigate to Search */ }
                        2 -> { /* Navigate to Profile */ }
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            when (selectedTab) {
                0 -> {
                    // Home Content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Welcome to Peeko!",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                        
                        Text(
                            text = "Home Screen",
                            style = MaterialTheme.typography.bodyLarge,
                            color = TextPrimary,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
                1 -> {
                    // Search Content
                    SearchScreen(navigationViewModel = navigationViewModel)
                }
                2 -> {
                    // Profile Content
                    ProfileScreen(navigationViewModel = navigationViewModel)
                }
            }
        }
    }
}