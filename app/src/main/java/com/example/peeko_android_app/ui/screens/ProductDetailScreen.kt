package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.peeko_android_app.R
import com.example.peeko_android_app.ui.theme.PeekoAndroidAppTheme
import com.example.peeko_android_app.ui.components.Product
import com.example.peeko_android_app.ui.theme.Gray100
import com.example.peeko_android_app.ui.theme.PrimaryBlue

// Utility function to convert Product to ProductDetail
fun Product.toProductDetail(): ProductDetail {
    return ProductDetail(
        id = this.id,
        name = this.name,
        price = this.price.replace("₹", "").toDoubleOrNull() ?: 0.0,
        originalPrice = (this.price.replace("₹", "").toDoubleOrNull() ?: 0.0) - 100,
        rating = this.rating,
        reviewCount = (50..200).random(), // Random review count
        description = "Premium quality ${this.name.lowercase()} made with finest materials. Perfect for everyday use with modern design and comfortable fit. Features durable construction and stylish appearance.",
        images = this.imageRes, // Using placeholder images
        sizes = listOf("S", "M", "L", "XL", "XXL"),
        colors = listOf(
            Color(0xFF4CAF50), // Green
            Color(0xFF2196F3), // Blue
            Color(0xFFFF5722), // Red
            Color(0xFFFFEB3B), // Yellow
            Color(0xFF9C27B0)  // Purple
        ),
        inStock = true
    )
}

data class ProductDetail(
    val id: String,
    val name: String,
    val price: Double,
    val originalPrice: Double? = null,
    val rating: Float,
    val reviewCount: Int,
    val description: String,
    val images: String,
    val sizes: List<String>,
    val colors: List<Color>,
    val inStock: Boolean = true
)

data class Review(
    val userName: String,
    val rating: Float,
    val comment: String,
    val date: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    product: Product? = null,
    onBackClick: () -> Unit = {},
    onAddToCart: (Product, Int) -> Unit = { _, _ -> }
) {
    // Use provided product or sample data
    val currentProduct = product ?: Product(
        id = "1",
        name = "Men's Harrington Jacket",
        price = "148",
        imageRes = "https://i.postimg.cc/nV6vDXst/cloth-4.jpg",
        rating = 4.5f,
        isFavorite = false
    )
    
    val productDetail = currentProduct.toProductDetail()
    var selectedImageIndex by remember { mutableStateOf(0) }
    var selectedSize by remember { mutableStateOf("") }
    var selectedColor by remember { mutableStateOf(Color.Transparent) }
    var quantity by remember { mutableStateOf(1) }
    var isFavorite by remember { mutableStateOf(false) }
    var showSizeSelector by remember { mutableStateOf(false) }
    var showColorSelector by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        item {
            // Top Navigation Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
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
                IconButton(onClick = {
                    isFavorite = !isFavorite
                }) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Black
                    )
                }
            }
        }

        item {
            // Product Image Gallery
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(horizontal = 16.dp)
            ) {
                AsyncImage(
                    model = productDetail.images,
                    contentDescription = productDetail.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }

        item {
            // Product Info Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = productDetail.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = productDetail.price.toString(),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                    productDetail.originalPrice?.let { originalPrice ->
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "₹${originalPrice}",
                            fontSize = 16.sp,
                            color = Color.Gray,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < productDetail.rating) Color(0xFFFFD700) else Color.Gray,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "${productDetail.rating} (${productDetail.reviewCount} reviews)",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = productDetail.description,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp
                )
            }
        }

        item {
            // Size Selection
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Size",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    TextButton(
                        onClick = { showSizeSelector = !showSizeSelector }
                    ) {
                        Text(
                            text = if (selectedSize.isEmpty()) "Select" else selectedSize,
                            color = PrimaryBlue
                        )
                        Icon(
                            imageVector = if (showSizeSelector) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = PrimaryBlue
                        )
                    }
                }

                if (showSizeSelector) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(productDetail.sizes) { size ->
                            Box(
                                modifier = Modifier
                                    .border(
                                        1.dp,
                                        if (selectedSize == size) Color(0xFF4CAF50) else Color.Gray,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(
                                        if (selectedSize == size) Color(0xFF4CAF50).copy(alpha = 0.1f) else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable {
                                        selectedSize = size
                                        showSizeSelector = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = size,
                                    color = if (selectedSize == size) Color(0xFF4CAF50) else Color.Black,
                                    fontWeight = if (selectedSize == size) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }
        }

        item {
            // Color Selection
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Color",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )
                    TextButton(
                        onClick = { showColorSelector = !showColorSelector }
                    ) {
                        if (selectedColor != Color.Transparent) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
                                    .clip(CircleShape)
                                    .background(selectedColor)
                                    .border(1.dp, Color.Gray, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = if (selectedColor == Color.Transparent) "Select" else "Selected",
                            color = PrimaryBlue
                        )
                        Icon(
                            imageVector = if (showColorSelector) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = PrimaryBlue
                        )
                    }
                }

                if (showColorSelector) {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(productDetail.colors) { color ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .border(
                                        2.dp,
                                        if (selectedColor == color) Color(0xFF4CAF50) else Color.Gray,
                                        CircleShape
                                    )
                                    .clickable {
                                        selectedColor = color
                                        showColorSelector = false
                                    }
                            ) {
                                if (selectedColor == color) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            // Quantity Selection
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Quantity",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                    ) {
                        Box(
                            modifier = Modifier.size(40.dp).background(Color.Gray.copy(alpha = 0.1f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "-",
                                color = Color.Black,
                                fontWeight = FontWeight.Medium,
                                fontSize = 34.sp,
                                modifier = Modifier.offset(y = -2.dp)
                            )
                        }
                    }

                    Text(
                        text = quantity.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    IconButton(
                        onClick = { quantity++ },) {
                        Box(
                            modifier = Modifier.size(40.dp).background(PrimaryBlue, CircleShape),
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

        item {
            // Shipping & Returns
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Shipping & Returns",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Free delivery within 60 minutes\n• Easy 30-day returns\n• Quality guarantee",
                        fontSize = 14.sp,
                        color = Color.Gray,
                        lineHeight = 20.sp
                    )
                }
            }
        }

        item {
            // Reviews Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${productDetail.rating} Ratings",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    TextButton(onClick = { }) {
                        Text(
                            text = "View All",
                            color = PrimaryBlue
                        )
                    }
                }

                // Sample Reviews
                val sampleReviews = listOf(
                    Review("John Smith", 5f, "Great quality jacket! Perfect fit and fast delivery.", "2 days ago"),
                    Review("Sarah Johnson", 4f, "Love the design and comfort. Highly recommend!", "1 week ago")
                )

                sampleReviews.forEach { review ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .border(1.dp, PrimaryBlue.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = review.userName,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.Black
                                )
                                Text(
                                    text = review.date,
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 4.dp)
                            ) {
                                repeat(5) { index ->
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = if (index < review.rating) Color(0xFFFFD700) else Color.Gray,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                            Text(
                                text = review.comment,
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    // Bottom Add to Bag Button
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { 
                        onAddToCart(currentProduct, quantity)
                    },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "₹${productDetail.price}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Add to Bag",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductDetailScreenPreview() {
    PeekoAndroidAppTheme {
        ProductDetailScreen()
    }
}