package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.components.CustomBottomNavigation
import com.example.peeko_android_app.ui.components.Product
import com.example.peeko_android_app.ui.components.ProductCard
import com.example.peeko_android_app.ui.theme.*
import com.example.peeko_android_app.R


data class Category(
    val name: String,
    val iconRes: String
)

data class OfferCard(
    val image: String,
)

@Composable
fun HomeScreen(
    navigationViewModel: NavigationViewModel,
    onProductClick: (Product) -> Unit = {},
    onCartClick: () -> Unit = {},
    cartItemCount: Int = 0
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    SetStatusBarColor(BackgroundPrimary, true)


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            CustomBottomNavigation(
                selectedTab = selectedTab,
                onTabSelected = { tabIndex ->
                    selectedTab = tabIndex
                    when (tabIndex) {
                        0 -> { /* Already on Home */ }
                        1 -> { /* Navigate to Search */ }
                        2 -> { /* Navigate to Profile */ }
                    }
                }
            )
        }
    ) { innerPadding ->
        when (selectedTab) {
            0 -> {
                HomeContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onProductClick = onProductClick,
                    onCartClick = onCartClick,
                    cartItemCount = cartItemCount
                )
            }
            1 -> {
                SearchScreen(
                    navigationViewModel = navigationViewModel,
                    onProductClick = onProductClick
                )
            }
            2 -> {
                ProfileScreen(navigationViewModel = navigationViewModel)
            }
        }
    }
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    onProductClick: (Product) -> Unit = {},
    onCartClick: () -> Unit = {},
    cartItemCount: Int = 0
) {
    Column(
        modifier = modifier
            .background(BackgroundPrimary)
            .verticalScroll(rememberScrollState())
    ) {
        // Profile Header
        ProfileHeader(
            onCartClick = onCartClick,
            cartItemCount = cartItemCount
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Categories Section
        CategoriesSection()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Offer Cards
        OfferCardsSection()
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Top Selling Section
        TopSellingSection(onProductClick = onProductClick)
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // New In Section
        NewInSection(onProductClick = onProductClick)
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ProfileHeader(
    onCartClick: () -> Unit = {},
    cartItemCount: Int = 0
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Profile Image
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Gray100),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_icon),
                    contentDescription = "Profile",
                    modifier = Modifier.size(24.dp),
                    contentScale = ContentScale.Fit
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Greeting and Location
            Column {
                Text(
                    text = "Good Mohit 👋",
                    fontSize = 16.sp,
                    color = TextPrimary
                )
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        text = "Kids Store",
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = TextPrimary
//                    )
//                    Icon(
//                        imageVector = Icons.Default.ArrowDropDown,
//                        contentDescription = "Dropdown",
//                        tint = TextPrimary,
//                        modifier = Modifier.size(20.dp)
//                    )
//                }
            }
        }
        
        // Shopping Cart Icon with Badge
        Box {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue)
                    .clickable { onCartClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                   imageVector = Icons.Default.ShoppingCart,
                    contentDescription = "Shopping Cart",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            // Cart Badge
            if (cartItemCount > 0) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(SecondaryOrange)
                        .align(Alignment.TopEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesSection() {
    val categories = listOf(
        Category("Toys", "https://i.postimg.cc/h47mcK2Z/category-toys.jpg"),
        Category("Clothes","https://i.postimg.cc/nV6vDXst/cloth-4.jpg"),
        Category("Shoes", "https://i.postimg.cc/s2XB8zBc/category-shoes.jpg"),
        Category("Food", "https://i.postimg.cc/NMZrmnjx/category-food.jpg"),
        Category("Games", "https://i.postimg.cc/L8xS5WRN/category-games.png")
    )
    
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Text(
            text = "Categories",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryItem(category = category)
            }
        }
    }
}

@Composable
fun CategoryItem(category: Category) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { /* Handle category click */ }
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(PrimaryBlue.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = category.iconRes,
                contentDescription = category.name,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.FillBounds
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = category.name,
            fontSize = 12.sp,
            color = TextPrimary,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun OfferCardsSection() {
    val offers = listOf(
        OfferCard(
            image = "https://i.postimg.cc/RhFJrzb9/offer-banner-2.jpg"
        ),
        OfferCard(
            image = "https://i.postimg.cc/XvS5rmXc/offer-banner-1.jpg"
        )
    )
    
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp)
    ) {
        items(offers) { offer ->
            OfferCardItem(offer = offer)
        }
    }
}

@Composable
fun OfferCardItem(offer: OfferCard) {
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(150.dp)
            .clickable { /* Handle offer click */ },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.1f))
    ) {
       AsyncImage(
           model = offer.image,
           contentDescription = "Banners",
           modifier = Modifier.fillMaxSize(),
           contentScale = ContentScale.FillBounds
       )
    }
}

@Composable
fun TopSellingSection(
    onProductClick: (Product) -> Unit = {}
) {
    val topSellingProducts = listOf(
        Product(
            id = "1",
            name = "Kids Teddy Bear",
            price = "₹2000",
            imageRes = "https://i.postimg.cc/nV6vDXst/cloth-4.jpg",
            rating = 4.8f,
            isFavorite = false
        ),
        Product(
            id = "2",
            name = "Baby Onesie Set",
            price = "₹1000",
            imageRes = "https://i.postimg.cc/h47mcK2Z/category-toys.jpg",
            rating = 4.9f,
            isFavorite = true
        ),
        Product(
            id = "3",
            name = "Kids Sneakers",
            price = "₹2400",
            imageRes = "https://i.postimg.cc/s2XB8zBc/category-shoes.jpg",
            rating = 4.7f,
            isFavorite = false
        )
    )
    
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Top Selling",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "See All",
                fontSize = 14.sp,
                color = PrimaryBlue,
                modifier = Modifier.clickable { /* Handle see all */ }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(topSellingProducts) { product ->
                ProductCard(
                    product = product,
                    onFavoriteClick = { /* Handle favorite */ },
                    onAddToCartClick = { /* Handle add to cart */ },
                    onProductClick = onProductClick
                )
            }
        }
    }
}

@Composable
fun NewInSection(
    onProductClick: (Product) -> Unit = {}
) {
    val newProducts = listOf(
        Product(
            id = "4",
            name = "Baby Skirt Set",
            price = "₹1800",
            imageRes = "https://i.postimg.cc/DZNwRbVf/cloth-1.jpg",
            rating = 4.6f,
            isFavorite = false
        ),
        Product(
            id = "5",
            name = "Kids Backpack Set",
            price = "₹2000",
            imageRes = "https://i.postimg.cc/L8xS5WRN/category-games.png",
            rating = 4.8f,
            isFavorite = true
        ),
        Product(
            id = "6",
            name = "Cotton Shirt",
            price = "₹2200",
            imageRes = "https://i.postimg.cc/mZcYkYgw/cloth-3.jpg",
            rating = 4.9f,
            isFavorite = false
        )
    )
    
    Column(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "New In",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
            Text(
                text = "See All",
                fontSize = 14.sp,
                color = PrimaryBlue,
                modifier = Modifier.clickable { /* Handle see all */ }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(newProducts) { product ->
                ProductCard(
                    product = product,
                    onFavoriteClick = { /* Handle favorite */ },
                    onAddToCartClick = { /* Handle add to cart */ },
                    onProductClick = onProductClick
                )
            }
        }
    }
}