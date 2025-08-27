package com.example.peeko_android_app.ui.screens

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peeko_android_app.R
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.theme.PrimaryBlue
import com.example.peeko_android_app.ui.theme.TextPrimary

data class RecentSearch(
    val text: String
)

data class Suggestion(
    val name: String,
    val category: String,
    val imageRes: Int
)

data class TopBrand(
    val name: String,
    val category: String,
    val imageRes: Int
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchScreen(
    navigationViewModel: NavigationViewModel
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
        Suggestion("Fisher-Price", "Toys", R.drawable.ic_launcher_foreground),
        Suggestion("Johnson's Baby", "Baby Care", R.drawable.ic_launcher_foreground)
    )
    
    val topBrands = listOf(
        TopBrand("Pampers", "Diapers", R.drawable.pampers_logo),
        TopBrand("Gerber", "Baby Food", R.drawable.gerber_logo),
        TopBrand("Carter's", "Baby Clothes", R.drawable.carters_logo)
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
                .padding(horizontal = 16.dp, vertical = 24.dp)
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
                items(suggestions) { suggestion ->
                    SuggestionCard(suggestion = suggestion)
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
            
            // Top Brands List
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                items(topBrands) { brand ->
                    TopBrandCard(brand = brand)
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun SuggestionCard(
    suggestion: Suggestion
) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .clickable { /* Handle suggestion click */ },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFF0F0F0)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = suggestion.imageRes),
                    contentDescription = suggestion.name,
                    modifier = Modifier.size(60.dp),
                    contentScale = ContentScale.Fit
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = suggestion.name,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                maxLines = 1
            )
            
            Text(
                text = suggestion.category,
                fontSize = 10.sp,
                color = Color.Gray,
                maxLines = 1
            )
        }
    }
}

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
             .clickable{}
             .background(Color(0xFFF0F0F0)),
         contentAlignment = Alignment.Center
     ){
         Image(
             painter = painterResource(id = brand.imageRes),
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