package com.example.peeko_android_app.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.peeko_android_app.data.Cart
import com.example.peeko_android_app.data.CartItem
import com.example.peeko_android_app.ui.components.Product

class CartViewModel : ViewModel() {
    var cart by mutableStateOf(Cart())
        private set
    
    fun addToCart(product: Product, quantity: Int = 1, selectedSize: String? = null, selectedColor: String? = null) {
        val cartItem = CartItem(
            product = product,
            quantity = quantity,
            selectedSize = selectedSize,
            selectedColor = selectedColor
        )
        cart = cart.addItem(cartItem)
    }
    
    fun removeFromCart(productId: String) {
        cart = cart.removeItem(productId)
    }
    
    fun updateQuantity(productId: String, newQuantity: Int) {
        cart = cart.updateQuantity(productId, newQuantity)
    }
    
    fun clearCart() {
        cart = Cart()
    }
    
    fun getCartItemCount(): Int {
        return cart.totalItems
    }
}