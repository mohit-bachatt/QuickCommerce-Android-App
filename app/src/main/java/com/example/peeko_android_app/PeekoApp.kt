package com.example.peeko_android_app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.peeko_android_app.navigation.AppScreen
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.screens.CartScreen
import com.example.peeko_android_app.ui.screens.CheckoutScreen
import com.example.peeko_android_app.ui.screens.CreateAccountScreen
import com.example.peeko_android_app.ui.screens.HomeScreen
import com.example.peeko_android_app.ui.screens.OnboardingScreen
import com.example.peeko_android_app.ui.screens.OrderPlacedScreen
import com.example.peeko_android_app.ui.screens.ProductDetailScreen
import com.example.peeko_android_app.ui.screens.SignInScreen
import com.example.peeko_android_app.ui.screens.SplashScreen
import com.example.peeko_android_app.ui.components.Product
import com.example.peeko_android_app.ui.theme.PeekoAndroidAppTheme
import com.example.peeko_android_app.viewmodel.CartViewModel

@Composable
fun PeekoApp() {
    val navigationViewModel: NavigationViewModel = viewModel()
    val cartViewModel: CartViewModel = viewModel()
    val navController = rememberNavController()
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    
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
                HomeScreen(
                    navigationViewModel = navigationViewModel,
                    onProductClick = { product ->
                        selectedProduct = product
                        navigationViewModel.navigateTo(AppScreen.ProductDetailScreen)
                    },
                    onCartClick = {
                        navigationViewModel.navigateTo(AppScreen.CartScreen)
                    },
                    cartItemCount = cartViewModel.getCartItemCount()
                )
            }
            
            composable(AppScreen.ProductDetailScreen.route) {
                ProductDetailScreen(
                    product = selectedProduct,
                    onBackClick = {
                        navigationViewModel.navigateBack()
                    },
                    onAddToCart = { selectedProd, quantity ->
                        cartViewModel.addToCart(selectedProd, quantity)

                        navigationViewModel.navigateTo(AppScreen.CartScreen)
                    }
                )
            }
            
            composable(AppScreen.CartScreen.route) {
                CartScreen(
                    cart = cartViewModel.cart,
                    onBackClick = {
                        navigationViewModel.navigateBack()
                    },
                    onCheckoutClick = {
                        navigationViewModel.navigateTo(AppScreen.CheckoutScreen)
                    },
                    onQuantityChange = { productId, newQuantity ->
                        cartViewModel.updateQuantity(productId, newQuantity)
                    },
                    onRemoveItem = { productId ->
                        cartViewModel.removeFromCart(productId)
                    }
                )
            }
            
            composable(AppScreen.CheckoutScreen.route) {
                CheckoutScreen(
                    cart = cartViewModel.cart,
                    onBackClick = {
                        navigationViewModel.navigateBack()
                    },
                    onPlaceOrderClick = {
                        navigationViewModel.navigateTo(AppScreen.OrderPlacedScreen)
                    }
                )
            }
            
            composable(AppScreen.OrderPlacedScreen.route) {
                OrderPlacedScreen(
                    onSeeOrderDetailsClick = {
                        // Navigate to order details (not implemented yet)
                        navigationViewModel.navigateTo(AppScreen.HomeScreen)
                    },
                    onContinueShoppingClick = {
                        cartViewModel.clearCart()
                        navigationViewModel.navigateTo(AppScreen.HomeScreen)
                    }
                )
            }
            
            // Add more screens here as needed
            // composable(AppScreen.LoginScreen.route) {
            //     LoginScreen(navigationViewModel = navigationViewModel)
            // }
        }
    }
}