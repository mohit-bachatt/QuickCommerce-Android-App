package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peeko_android_app.R
import com.example.peeko_android_app.ui.theme.PeekoAndroidAppTheme
import com.example.peeko_android_app.ui.theme.PrimaryBlue
import com.example.peeko_android_app.ui.theme.SecondaryOrange
import com.example.peeko_android_app.ui.theme.TextPrimary
import com.example.peeko_android_app.ui.theme.TextSecondary

@Composable
fun OrderPlacedScreen(
    orderNumber: String = "#12345",
    onSeeOrderDetailsClick: () -> Unit = {},
    onContinueShoppingClick: () -> Unit = {}
) {

    SetStatusBarColor(Color(0xFF52B1AD), true)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                PrimaryBlue
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(top = 180.dp, bottom = 30.dp, start = 16.dp, end = 16.dp),)
            {
                // Success Illustration
                Box(
                    modifier = Modifier
                        .size(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.order_placed),
                        contentDescription = "Success Illustration",
                        modifier = Modifier.size(250.dp),
                        contentScale = ContentScale.FillBounds
                    )
                }
            }


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Clip to rounded shape
                    .background(Color.White) // Apply background after clipping
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Success Title
                Text(
                    text = "Order Placed Successfully",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Success Message
                Text(
                    text = "You will receive an email confirmation",
                    fontSize = 16.sp,
                    color = Color.Black.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Order Number
                Text(
                    text = "Order $orderNumber",
                    fontSize = 14.sp,
                    color = Color.Black.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(60.dp))

                // See Order Details Button
                Button(
                    onClick = onSeeOrderDetailsClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryBlue
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "See Order Details",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Continue Shopping Button
                Button(
                    onClick = onContinueShoppingClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Continue Shopping",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = PrimaryBlue
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderPlacedScreenPreview() {
    PeekoAndroidAppTheme {
        OrderPlacedScreen(
            orderNumber = "#12345"
        )
    }
}