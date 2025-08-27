package com.example.peeko_android_app.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import coil.compose.AsyncImage
import com.example.peeko_android_app.R
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.components.Product
import com.example.peeko_android_app.ui.components.ProductCard
import com.example.peeko_android_app.ui.theme.PrimaryBlue
import com.example.peeko_android_app.ui.theme.TextPrimary

data class RecentSearch(
    val text: String
)

// Removed Suggestion data class - using Product from ProductCard component

data class TopBrand(
    val name: String,
    val category: String,
    val imageRes: String
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    navigationViewModel: NavigationViewModel,
    onProductClick: (Product) -> Unit = {}
) {
    var searchText by remember { mutableStateOf("") }
    
    val recentSearches = listOf(
        RecentSearch("Baby Bottles"),
        RecentSearch("Diapers"),
        RecentSearch("Baby Food"),
        RecentSearch("Toys"),
        RecentSearch("Strollers"),
        RecentSearch("Baby Clothes"),
        RecentSearch("Pacifiers"),
        RecentSearch("Baby Monitors")
    )
    
    val suggestions = listOf(
        Product(
            id = "1",
            name = "Mike Skirts Club",
            price = "₹ 2000",
            rating = 4.9f,
            imageRes = "https://i.postimg.cc/DZNwRbVf/cloth-1.jpg",
            isFavorite = false
        ),
        Product(
            id = "2",
            name = "Lacoste Combo",
            price = "₹2500",
            rating = 5.0f,
            imageRes = "https://i.postimg.cc/VvbkwL1q/cloth-2.jpg",
            isFavorite = true
        ),
        Product(
            id = "3",
            name = "BabyCotton Shirt",
            price = "₹1500",
            rating = 4.8f,
            imageRes = "https://i.postimg.cc/mZcYkYgw/cloth-3.jpg",
            isFavorite = false
        ),
        Product(
            id = "4",
            name = "Fisher-Price Shirt",
            price = "₹1000",
            rating = 4.7f,
            imageRes = "https://i.postimg.cc/nV6vDXst/cloth-4.jpg",
            isFavorite = false
        )
    )
    
    val topBrands = listOf(
        TopBrand("Pampers", "Diapers", "https://i.postimg.cc/wTp1zLy3/pampers-logo.png"),
        TopBrand("Gerber", "Baby Food", "https://i.postimg.cc/1z2zjq1m/gerber-logo.png"),
        TopBrand("Carter's", "Baby Clothes", "https://i.postimg.cc/FsV7Hz3p/carters-logo.jpg")
    )

    SetStatusBarColor(Color(0xFF52B1AD), false)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Header with gradient background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF52B1AD), // PrimaryBlue
                            Color(0xFF6DCFCB), // lighter teal
                            Color(0xFF3A8E8A)  // deeper teal
                        )
                    ), shape = RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 44.dp)
        ) {
            // Search Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(12.dp))
                    
                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        modifier = Modifier.weight(1f),
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            color = TextPrimary
                        ),
                        decorationBox = { innerTextField ->
                            if (searchText.isEmpty()) {
                                Text(
                                    text = "Search",
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            }
                            innerTextField()
                        }
                    )
                }
            }
        }
        
        // Content
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Recent Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Recent",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                Text(
                    text = "Clear all",
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { /* Clear all recent searches */ }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Recent Search Chips
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                recentSearches.forEach { search ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color(0xFFF5F5F5),
                        modifier = Modifier.clickable { /* Handle search click */ }
                    ) {
                        Text(
                            text = search.text,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            color = TextPrimary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Suggestions Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Suggestions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                Text(
                    text = "See all",
                    color = PrimaryBlue,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { /* See all suggestions */ }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Suggestions List
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                items(suggestions) { product ->
                    ProductCard(
                        product = product,
                        onProductClick = { onProductClick(product) },
                        onFavoriteClick = { /* Handle favorite click */ },
                        onAddToCartClick = { /* Handle add to cart */ }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Top Brands Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Top Brands",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                
                Text(
                    text = "See all",
                    color = PrimaryBlue,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { /* See all brands */ }
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                items(topBrands) { brand ->
                    TopBrandCard(brand = brand)
                }
            }
            
            Spacer(modifier = Modifier.height(172.dp))
        }
    }
}

// SuggestionCard removed - replaced with ProductCard component

@Composable
fun TopBrandCard(
    brand: TopBrand
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable {}
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = brand.imageRes,
                contentDescription = brand.name,
                modifier = Modifier.size(100.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        Text(
            text = brand.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary,
            maxLines = 1
        )
    }
}