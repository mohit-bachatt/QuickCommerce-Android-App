package com.example.peeko_android_app.ui.screens

import android.annotation.SuppressLint
import android.app.Activity
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.theme.PrimaryBlue
import com.example.peeko_android_app.ui.theme.TextPrimary

data class ProfileMenuItem(
    val icon: ImageVector,
    val title: String,
    val hasArrow: Boolean = true,
    val hasSwitch: Boolean = false,
    val switchState: Boolean = false,
    val badge: String? = null,
    val isLogout: Boolean = false
)

@SuppressLint("ContextCastToActivity")
@Composable
fun ProfileScreen(
    navigationViewModel: NavigationViewModel
) {
    var pushNotifications by remember { mutableStateOf(true) }
    var faceId by remember { mutableStateOf(true) }

    SetStatusBarColor(Color(0xFFF8F9FA), true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Profile Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Profile Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE8F5E8)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    modifier = Modifier.size(50.dp),
                    tint = PrimaryBlue
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Mohit Chauhan",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )

            Text(
                text = "itsmohitchauhan1409@gmail.com",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Edit profile */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.padding(horizontal = 32.dp)
            ) {
                Text(
                    text = "Edit profile",
                    color = Color.White,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Account Section
        ProfileSection(
            title = "Account",
            items = listOf(
                ProfileMenuItem(
                    icon = Icons.Default.ShoppingCart,
                    title = "My orders",
                    badge = "3"
                ),
                ProfileMenuItem(
                    icon = Icons.Default.FavoriteBorder,
                    title = "Wishlist"
                ),
                ProfileMenuItem(
                    icon = Icons.Default.Home,
                    title = "Addresses"
                ),
                ProfileMenuItem(
                    icon = Icons.Default.Settings,
                    title = "Payment methods"
                )
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Preferences Section
        ProfileSection(
            title = "Preferences",
            items = listOf(
                ProfileMenuItem(
                    icon = Icons.Default.Notifications,
                    title = "Push notifications",
                    hasArrow = false,
                    hasSwitch = true,
                    switchState = pushNotifications
                ),
                ProfileMenuItem(
                    icon = Icons.Default.Face,
                    title = "Face ID",
                    hasArrow = false,
                    hasSwitch = true,
                    switchState = faceId
                ),
                ProfileMenuItem(
                    icon = Icons.Default.Lock,
                    title = "PIN Code"
                )
            ),
            onSwitchToggle = { index, state ->
                when (index) {
                    0 -> pushNotifications = state
                    1 -> faceId = state
                }
            }
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Logout Section
        ProfileSection(
            title = "",
            items = listOf(
                ProfileMenuItem(
                    icon = Icons.Default.ExitToApp,
                    title = "Logout",
                    hasArrow = false,
                    isLogout = true
                )
            )
        )
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun ProfileSection(
    title: String,
    items: List<ProfileMenuItem>,
    onSwitchToggle: ((Int, Boolean) -> Unit)? = null
) {
    if (title.isNotEmpty()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 12.dp)
        )
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column {
            items.forEachIndexed { index, item ->
                ProfileMenuItem(
                    item = item,
                    onSwitchToggle = { state ->
                        onSwitchToggle?.invoke(index, state)
                    }
                )
                
                if (index < items.size - 1) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color(0xFFF0F0F0))
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileMenuItem(
    item: ProfileMenuItem,
    onSwitchToggle: ((Boolean) -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle click */ }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            modifier = Modifier.size(24.dp),
            tint = if (item.isLogout) Color.Red else Color.Gray
        )
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Text(
            text = item.title,
            style = MaterialTheme.typography.bodyLarge,
            color = if (item.isLogout) Color.Red else TextPrimary,
            modifier = Modifier.weight(1f)
        )
        
        // Badge
        item.badge?.let { badge ->
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(PrimaryBlue),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badge,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
        
        // Switch
        if (item.hasSwitch) {
            Switch(
                checked = item.switchState,
                onCheckedChange = { onSwitchToggle?.invoke(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = PrimaryBlue,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }
        
        // Arrow
        if (item.hasArrow) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Arrow",
                modifier = Modifier.size(20.dp),
                tint = Color.Gray
            )
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean = true) {
    val activity = LocalContext.current as Activity
    SideEffect {
        val window = activity.window
        window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = darkIcons
    }
}