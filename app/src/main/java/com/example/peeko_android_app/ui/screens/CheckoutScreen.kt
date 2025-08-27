package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peeko_android_app.R
import com.example.peeko_android_app.data.Cart
import com.example.peeko_android_app.data.CartItem
import com.example.peeko_android_app.ui.components.Product
import com.example.peeko_android_app.ui.theme.Gray100
import com.example.peeko_android_app.ui.theme.PeekoAndroidAppTheme
import com.example.peeko_android_app.ui.theme.PrimaryBlue
import com.example.peeko_android_app.ui.theme.SecondaryOrange
import com.example.peeko_android_app.ui.theme.TextPrimary
import com.example.peeko_android_app.ui.theme.TextSecondary

data class ShippingAddress(
    val fullName: String,
    val address: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val isDefault: Boolean = false
)

data class PaymentMethod(
    val id: String,
    val type: String, // "card" or "paypal"
    val displayName: String,
    val lastFourDigits: String? = null,
    val isDefault: Boolean = false
)

@Composable
fun CheckoutScreen(
    cart: Cart,
    onBackClick: () -> Unit = {},
    onPlaceOrderClick: () -> Unit = {},
    onAddShippingAddressClick: () -> Unit = {},
    onAddPaymentMethodClick: () -> Unit = {}
) {
    var selectedShippingAddress by remember { mutableStateOf<ShippingAddress?>(null) }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    
    // Sample data
    val sampleAddresses = listOf(
        ShippingAddress(
            fullName = "John Doe",
            address = "2715 Ash Dr. San Jose, South Dakota 83475",
            city = "San Jose",
            state = "South Dakota",
            zipCode = "83475",
            isDefault = true
        )
    )
    
    val samplePaymentMethods = listOf(
        PaymentMethod(
            id = "1",
            type = "card",
            displayName = "**** 4187",
            lastFourDigits = "4187",
            isDefault = true
        )
    )
    
    // Set default selections
    if (selectedShippingAddress == null && sampleAddresses.isNotEmpty()) {
        selectedShippingAddress = sampleAddresses.first()
    }
    if (selectedPaymentMethod == null && samplePaymentMethods.isNotEmpty()) {
        selectedPaymentMethod = samplePaymentMethods.first()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Navigation Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Gray100, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }
            }
            Text(
                text = "Checkout",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            item {
                // Shipping Address Section
                CheckoutSection(
                    title = "Shipping Address",
                    onAddClick = onAddShippingAddressClick
                ) {
                    if (sampleAddresses.isNotEmpty()) {
                        sampleAddresses.forEach { address ->
                            ShippingAddressCard(
                                address = address,
                                isSelected = selectedShippingAddress == address,
                                onSelect = { selectedShippingAddress = address }
                            )
                        }
                    } else {
                        EmptyStateCard(
                            title = "No shipping address",
                            subtitle = "Add your shipping address",
                            onAddClick = onAddShippingAddressClick
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Payment Method Section
                CheckoutSection(
                    title = "Payment Method",
                    onAddClick = onAddPaymentMethodClick
                ) {
                    if (samplePaymentMethods.isNotEmpty()) {
                        samplePaymentMethods.forEach { paymentMethod ->
                            PaymentMethodCard(
                                paymentMethod = paymentMethod,
                                isSelected = selectedPaymentMethod == paymentMethod,
                                onSelect = { selectedPaymentMethod = paymentMethod }
                            )
                        }
                    } else {
                        EmptyStateCard(
                            title = "No payment method",
                            subtitle = "Add your payment method",
                            onAddClick = onAddPaymentMethodClick
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
        
        // Bottom Order Summary
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(1.dp, PrimaryBlue.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                // Order Summary
                Text(
                    text = "Order Summary",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Subtotal
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Subtotal",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = "₹${String.format("%.2f", cart.subtotal)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Shipping Cost
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Shipping Cost",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = "₹${String.format("%.2f", cart.shippingCost)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                // Tax
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Tax",
                        fontSize = 14.sp,
                        color = TextSecondary
                    )
                    Text(
                        text = "₹${String.format("%.2f", cart.tax)}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary
                    )
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Divider(color = Gray100)
                
                Spacer(modifier = Modifier.height(12.dp))
                
                // Total
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Total",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(
                        text = "₹${String.format("%.2f", cart.total)}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Button(
                    onClick = onPlaceOrderClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = selectedShippingAddress != null && selectedPaymentMethod != null
                ) {
                    Text(
                        text = "Place Order",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CheckoutSection(
    title: String,
    onAddClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "Add",
                fontSize = 14.sp,
                color = PrimaryBlue,
                modifier = Modifier.clickable { onAddClick() }
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        content()
    }
}

@Composable
fun ShippingAddressCard(
    address: ShippingAddress,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PrimaryBlue.copy(alpha = 0.05f) else Color.White
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, PrimaryBlue)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, Gray100)
        },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp,  end = 16.dp),
            verticalAlignment = Alignment.Top
        ) {

//            RadioButton(
//                selected = isSelected,
//                onClick = onSelect,
//                colors = RadioButtonDefaults.colors(
//                    selectedColor = PrimaryBlue
//                ),
//                modifier = Modifier.offset(y = -8.dp)
//            )
            

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = address.fullName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Text(
                    text = address.address,
                    fontSize = 14.sp,
                    color = TextSecondary,
                    lineHeight = 20.sp
                )
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Edit",
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun PaymentMethodCard(
    paymentMethod: PaymentMethod,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onSelect() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PrimaryBlue.copy(alpha = 0.05f) else Color.White
        ),
        border = if (isSelected) {
            androidx.compose.foundation.BorderStroke(1.dp, PrimaryBlue)
        } else {
            androidx.compose.foundation.BorderStroke(1.dp, Gray100)
        },
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp, bottom = 16.dp,  end = 16.dp),
            verticalAlignment = Alignment.Top
        ) {
//            RadioButton(
//                selected = isSelected,
//                onClick = onSelect,
//                colors = RadioButtonDefaults.colors(
//                    selectedColor = PrimaryBlue
//                ),
//                modifier = Modifier.offset(y = -8.dp)
//
//            )
            

            // Payment method icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (paymentMethod.type == "card") Color(0xFFE3F2FD) else Color(0xFFFFF3E0),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (paymentMethod.type == "card") {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Credit Card",
                        tint = PrimaryBlue,
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    // PayPal icon placeholder
                    Text(
                        text = "PP",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = SecondaryOrange
                    )
                }
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = paymentMethod.displayName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = TextPrimary
                )
                
                if (paymentMethod.type == "card") {
                    Text(
                        text = "Expires 12/25",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Edit",
                tint = TextSecondary,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun EmptyStateCard(
    title: String,
    subtitle: String,
    onAddClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onAddClick() },
        colors = CardDefaults.cardColors(
            containerColor = Gray100.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = TextPrimary
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = subtitle,
                fontSize = 14.sp,
                color = TextSecondary
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    PeekoAndroidAppTheme {
        val sampleCart = Cart(
            items = listOf(
                CartItem(
                    product = Product(
                        id = "1",
                        name = "Men's Harrington Jacket",
                        price = "$148",
                        rating = 4.5f,
                        imageRes = "https://example.com/jacket.jpg"
                    ),
                    quantity = 2
                )
            )
        )
        CheckoutScreen(
            cart = sampleCart
        )
    }
}