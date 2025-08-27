package com.example.peeko_android_app.navigation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavigationViewModel : ViewModel() {
    private var _navController: NavController? = null
    val navController: NavController?
        get() = _navController
    
    private val _currentScreen = MutableStateFlow<AppScreen>(AppScreen.SplashScreen)
    val currentScreen: StateFlow<AppScreen> = _currentScreen.asStateFlow()
    
    fun setNavController(navController: NavController) {
        _navController = navController
    }
    
    fun navigateTo(screen: AppScreen) {
        _navController?.navigate(screen.route) {
            // Clear back stack when navigating from splash
            if (_currentScreen.value == AppScreen.SplashScreen) {
                popUpTo(AppScreen.SplashScreen.route) {
                    inclusive = true
                }
            }
        }
        _currentScreen.value = screen
    }
    
    fun navigateBack() {
        _navController?.popBackStack()
    }
    
    fun updateCurrentScreen(screen: AppScreen) {
        _currentScreen.value = screen
    }
    
    fun navigateToSignIn() {
        navigateTo(AppScreen.SignInScreen)
    }
    
    fun navigateToCreateAccount() {
        navigateTo(AppScreen.CreateAccountScreen)
    }
    
    fun navigateToHome() {
        navigateTo(AppScreen.HomeScreen)
    }
}