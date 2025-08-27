package com.example.peeko_android_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peeko_android_app.navigation.AppScreen
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.screens.CreateAccountScreen
import com.example.peeko_android_app.ui.screens.HomeScreen
import com.example.peeko_android_app.ui.screens.OnboardingScreen
import com.example.peeko_android_app.ui.screens.SignInScreen
import com.example.peeko_android_app.ui.screens.SplashScreen
import com.example.peeko_android_app.ui.theme.PeekoAndroidAppTheme

@Composable
fun PeekoApp() {
    val navigationViewModel: NavigationViewModel = viewModel()
    val navController = rememberNavController()
    
    // Set up the nav controller in the view model
    LaunchedEffect(navController) {
        navigationViewModel.setNavController(navController)
    }
    
    PeekoAndroidAppTheme {
        NavHost(
            navController = navController,
            startDestination = AppScreen.SplashScreen.route
        ) {
            composable(AppScreen.SplashScreen.route) {
                SplashScreen(navigationViewModel = navigationViewModel)
            }
            
            composable(AppScreen.OnboardingScreen.route) {
                OnboardingScreen(navigationViewModel = navigationViewModel)
            }
            
            composable(AppScreen.SignInScreen.route) {
                SignInScreen(navigationViewModel = navigationViewModel)
            }
            
            composable(AppScreen.CreateAccountScreen.route) {
                CreateAccountScreen(navigationViewModel = navigationViewModel)
            }
            
            composable(AppScreen.HomeScreen.route) {
                HomeScreen(navigationViewModel = navigationViewModel)
            }
            
            // Add more screens here as needed
            // composable(AppScreen.LoginScreen.route) {
            //     LoginScreen(navigationViewModel = navigationViewModel)
            // }
        }
    }
}