package com.example.peeko_android_app.navigation

sealed class AppScreen(val route: String) {
    object SplashScreen : AppScreen("splash_screen")
    object OnboardingScreen : AppScreen("onboarding_screen")
    object SignInScreen : AppScreen("signin_screen")
    object CreateAccountScreen : AppScreen("create_account_screen")
    object HomeScreen : AppScreen("home_screen")
    object LoginScreen : AppScreen("login_screen")
    object RegisterScreen : AppScreen("register_screen")
    object ProfileScreen : AppScreen("profile_screen")
    object SettingsScreen : AppScreen("settings_screen")
    
    companion object {
        fun fromRoute(route: String?): AppScreen {
            return when (route) {
                SplashScreen.route -> SplashScreen
                OnboardingScreen.route -> OnboardingScreen
                SignInScreen.route -> SignInScreen
                CreateAccountScreen.route -> CreateAccountScreen
                HomeScreen.route -> HomeScreen
                LoginScreen.route -> LoginScreen
                RegisterScreen.route -> RegisterScreen
                ProfileScreen.route -> ProfileScreen
                SettingsScreen.route -> SettingsScreen
                else -> SplashScreen
            }
        }
    }
}