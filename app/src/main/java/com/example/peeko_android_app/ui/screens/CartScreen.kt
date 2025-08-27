package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.peeko_android_app.R
import com.example.peeko_android_app.data.Cart
import com.example.peeko_android_app.data.CartItem
import com.example.peeko_android_app.ui.components.Product
import com.example.peeko_android_app.ui.theme.Gray100
import com.example.peeko_android_app.ui.theme.Gray500
import com.example.peeko_android_app.ui.theme.Gray900
import com.example.peeko_android_app.ui.theme.PeekoAndroidAppTheme
import com.example.peeko_android_app.ui.theme.PrimaryBlue
import com.example.peeko_android_app.ui.theme.SecondaryOrange
import com.example.peeko_android_app.ui.theme.TextPrimary
import com.example.peeko_android_app.ui.theme.TextSecondary

@Composable
fun CartScreen(
    cart: Cart,
    onBackClick: () -> Unit = {},
    onCheckoutClick: () -> Unit = {},
    onQuantityChange: (String, Int) -> Unit = { _, _ -> },
    onRemoveItem: (String) -> Unit = {},
    onExploreCategoriesClick: () -> Unit = {}
) {
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
                text = "Cart",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            if (cart.items.isNotEmpty()) {
                TextButton(onClick = { /* Clear cart */ }) {
                    Text(
                        text = "Remove All",
                        color = SecondaryOrange,
                        fontSize = 14.sp
                    )
                }
            } else {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }

        if (cart.items.isEmpty()) {
            // Empty Cart State
            EmptyCartContent(onExploreCategoriesClick = onExploreCategoriesClick)
        } else {
            // Cart with Items
            CartWithItemsContent(
                cart = cart,
                onCheckoutClick = onCheckoutClick,
                onQuantityChange = onQuantityChange,
                onRemoveItem = onRemoveItem
            )
        }
    }
}

@Composable
fun EmptyCartContent(
    onExploreCategoriesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Shopping bag icon
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(
                    color = SecondaryOrange.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "Empty Cart",
                tint = SecondaryOrange,
                modifier = Modifier.size(60.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Your Cart is Empty",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Looks like you haven't added anything to your cart yet",
            fontSize = 16.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Button(
            onClick = onExploreCategoriesClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Explore Categories",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}

@Composable
fun CartWithItemsContent(
    cart: Cart,
    onCheckoutClick: () -> Unit,
    onQuantityChange: (String, Int) -> Unit,
    onRemoveItem: (String) -> Unit
) {
    var couponCode by remember { mutableStateOf("") }
    
    LazyColumn(
        modifier = Modifier
    ) {
        items(cart.items) { cartItem ->
            CartItemCard(
                cartItem = cartItem,
                onQuantityChange = { newQuantity ->
                    onQuantityChange(cartItem.product.id, newQuantity)
                },
                onRemoveClick = {
                    onRemoveItem(cartItem.product.id)
                }
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
            
            // Coupon Code Section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Gray100.copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = couponCode,
                        onValueChange = { couponCode = it },
                        placeholder = {
                            Text(
                                text = "Enter Coupon Code",
                                color = TextSecondary,
                                fontSize = 14.sp
                            )
                        },
                        textStyle = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp
                        ),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PrimaryBlue,
                            unfocusedBorderColor = Gray500,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(1f).height(56.dp),
                        singleLine = true
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    Button(
                        onClick = { /* Apply coupon */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Text(
                            text = "Apply",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
    
    // Bottom Summary Section
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
            // Subtotal
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Subtotal",
                    fontSize = 16.sp,
                    color = TextSecondary
                )
                Text(
                    text = "₹${String.format("%.2f", cart.subtotal)}",
                    fontSize = 16.sp,
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
                    fontSize = 16.sp,
                    color = TextSecondary
                )
                Text(
                    text = "₹${String.format("%.2f", cart.shippingCost)}",
                    fontSize = 16.sp,
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
                    fontSize = 16.sp,
                    color = TextSecondary
                )
                Text(
                    text = "₹${String.format("%.2f", cart.tax)}",
                    fontSize = 16.sp,
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
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    text = "₹${String.format("%.2f", cart.total)}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(
                onClick = onCheckoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Checkout",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CartItemCard(
    cartItem: CartItem,
    onQuantityChange: (Int) -> Unit,
    onRemoveClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .border(1.dp, Gray500.copy(alpha = 0.3f), RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            AsyncImage(
                model = cartItem.product.imageRes,
                contentDescription = cartItem.product.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Gray100),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Product info (center section)
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    Text(
                        text = cartItem.product.name,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = TextPrimary,
                        maxLines = 2
                    )

                    Spacer(modifier = Modifier.height(4.dp))


                    // Delete button at right
                    IconButton(
                        onClick = onRemoveClick,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remove item",
                            tint = SecondaryOrange,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Price + Quantity controls
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cartItem.product.price,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Decrease
                        IconButton(
                            onClick = {
                                if (cartItem.quantity > 1) {
                                    onQuantityChange(cartItem.quantity - 1)
                                }
                            },
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(Color.Gray.copy(alpha = 0.1f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "-",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                            }
                        }

                        Text(
                            text = cartItem.quantity.toString(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = TextPrimary,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        // Increase
                        IconButton(
                            onClick = { onQuantityChange(cartItem.quantity + 1) }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(PrimaryBlue, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Increase",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CartScreenEmptyPreview() {
    PeekoAndroidAppTheme {
        CartScreen(
            cart = Cart()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CartScreenWithItemsPreview() {
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
                    quantity = 2,
                    selectedSize = "M",
                    selectedColor = "Green"
                ),
                CartItem(
                    product = Product(
                        id = "2",
                        name = "Men's Denim Jacket",
                        price = "$89",
                        rating = 4.2f,
                        imageRes = "https://example.com/denim.jpg"
                    ),
                    quantity = 1,
                    selectedSize = "L",
                    selectedColor = "Black"
                )
            )
        )
        CartScreen(
            cart = sampleCart
        )
    }
}