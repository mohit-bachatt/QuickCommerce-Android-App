package com.example.peeko_android_app.data

import com.example.peeko_android_app.ui.components.Product

data class CartItem(
    val product: Product,
    val quantity: Int,
    val selectedSize: String? = null,
    val selectedColor: String? = null
) {
    val totalPrice: Double
        get() = product.price.replace("$", "").toDoubleOrNull()?.times(quantity) ?: 0.0
}

data class Cart(
    val items: List<CartItem> = emptyList()
) {
    val totalItems: Int
        get() = items.sumOf { it.quantity }
    
    val subtotal: Double
        get() = items.sumOf { it.totalPrice }
    
    val shippingCost: Double = 5.00
    
    val tax: Double
        get() = subtotal * 0.08 // 8% tax
    
    val total: Double
        get() = subtotal + shippingCost + tax
    
    fun addItem(item: CartItem): Cart {
        val existingItemIndex = items.indexOfFirst { 
            it.product.id == item.product.id && 
            it.selectedSize == item.selectedSize && 
            it.selectedColor == item.selectedColor 
        }
        
        return if (existingItemIndex >= 0) {
            val updatedItems = items.toMutableList()
            updatedItems[existingItemIndex] = updatedItems[existingItemIndex].copy(
                quantity = updatedItems[existingItemIndex].quantity + item.quantity
            )
            copy(items = updatedItems)
        } else {
            copy(items = items + item)
        }
    }
    
    fun removeItem(productId: String): Cart {
        return copy(items = items.filter { it.product.id != productId })
    }
    
    fun updateQuantity(productId: String, newQuantity: Int): Cart {
        val updatedItems = items.map { item ->
            if (item.product.id == productId) {
                item.copy(quantity = newQuantity)
            } else {
                item
            }
        }.filter { it.quantity > 0 }
        
        return copy(items = updatedItems)
    }
}