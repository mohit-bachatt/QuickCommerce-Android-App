package com.example.peeko_android_app.ui.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.peeko_android_app.R
import com.example.peeko_android_app.navigation.AppScreen
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.theme.PrimaryBlue
import kotlinx.coroutines.launch

data class OnboardingPage(
    val imageNo: Int,
    val imageRes: String,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    navigationViewModel: NavigationViewModel
) {
    val pages = listOf(
        OnboardingPage(
            imageNo = 1,
            imageRes = "https://i.postimg.cc/FR4Z31CR/splash-1.jpg ",
            title = "Welcome to Peeko",
            description = ""
        ),
        OnboardingPage(
            imageNo = 2,
            imageRes = "https://i.postimg.cc/63trTgT4/splash-2.jpg",
            title = "Fast Delivery",
            description = "Get your essentials delivered in under 60 minutes"
        ),
        OnboardingPage(
            imageNo = 3,
            imageRes = "https://i.postimg.cc/DyH1188T/splash-3.jpg",
            title = "Quality Products",
            description = "Curated selection of safe and premium products for your little ones"
        )
    )
    
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            OnboardingPageContent(
                page = pages[page],
                pageIndex = page,
                currentPage = pagerState.currentPage
            )
        }
        
        // Bottom content overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.7f)
                        )
                    )
                )
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (pagerState.currentPage != pages.size - 1) {
                    // Page indicators
                    PageIndicators(
                        pageCount = pages.size,
                        currentPage = pagerState.currentPage
                    )
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Show button only on last page
                if (pagerState.currentPage == pages.size - 1) {
                    Button(
                        onClick = {
                            navigationViewModel.navigateTo(AppScreen.SignInScreen)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PrimaryBlue
                        ),
                        shape = RoundedCornerShape(28.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Start Your Journey",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Back",
                                tint = Color.White,
                                modifier = Modifier.size(24.dp).offset(y = 1.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPageContent(
    page: OnboardingPage,
    pageIndex: Int,
    currentPage: Int
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image - simplified without complex animations
        AsyncImage(
            model = page.imageRes,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.splash_1)
        )
        
        // Gradient overlay for better text readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.4f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )
        
        // Text content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(top = if(page.imageNo == 1) 100.dp else if(page.imageNo == 2) 600.dp else 650.dp),
            horizontalAlignment = if(page.imageNo != 3 )Alignment.CenterHorizontally else Alignment.Start
        ) {
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = if(page.imageNo != 3 ) TextAlign.Center else TextAlign.Start,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun PageIndicators(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(pageCount) { index ->
            val isSelected = index == currentPage
            
            Box(
                modifier = Modifier
                    .size(if (isSelected) 12.dp else 8.dp)
                    .clip(CircleShape)
                    .background(
                        if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
                    )
            )
        }
    }
}