package com.example.peeko_android_app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peeko_android_app.R
import com.example.peeko_android_app.ui.theme.Black
import com.example.peeko_android_app.ui.theme.PrimaryBlue

data class BottomNavItem(
    val label: String,
    val iconRes: Int,
    val isSelected: Boolean = false
)

@Composable
fun CustomBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val navItems = listOf(
        BottomNavItem(
            label = "Home",
            iconRes = R.drawable.home_icon,
            isSelected = selectedTab == 0
        ),
        BottomNavItem(
            label = "Search",
            iconRes = R.drawable.search_icon,
            isSelected = selectedTab == 1
        ),
        BottomNavItem(
            label = "Profile",
            iconRes = R.drawable.profile_icon,
            isSelected = selectedTab == 2
        )
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 52.dp)
                .padding( top = 5.dp, bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home Tab
            BottomNavTab(
                item = navItems[0],
                onClick = { onTabSelected(0) }
            )

            // Search Tab (Elevated)
            Box(
                modifier = Modifier.offset(y = (-30).dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(PrimaryBlue)
                        .clickable { onTabSelected(1) },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.search_icon),
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            // Profile Tab
            BottomNavTab(
                item = navItems[2],
                onClick = { onTabSelected(2) }
            )
        }
    }
}

@Composable
fun BottomNavTab(
    item: BottomNavItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick() }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(if (item.isSelected) PrimaryBlue.copy(alpha = 0.2f) else Color.Transparent),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = item.iconRes),
                contentDescription = item.label,
                tint = if (item.isSelected) Black else Color.Unspecified,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
//
//        Text(
//            text = item.label,
//            fontSize = 12.sp,
//            fontWeight = if (item.isSelected) FontWeight.Bold else FontWeight.Normal,
//            color = if (item.isSelected) PrimaryBlue else Color.Gray
//        )
    }
}