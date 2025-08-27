package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.R
import com.example.peeko_android_app.ui.theme.PrimaryBlue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navigationViewModel: NavigationViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))

        // Title
        Text(
            text = "Sign in",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Email Input Field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = {
                Text(
                    text = "Email Address",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Password Input Field
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = {
                Text(
                    text = "Password",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5)
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Continue Button
        Button(
            onClick = {
                // Handle sign in logic here
                navigationViewModel.navigateToHome()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF52B1AD)
            )
        ) {
            Text(
                text = "Continue",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Create Account Link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Dont have an Account ? ",
                color = Color.Black,
                fontSize = 14.sp
            )
            Text(
                text = "Create One",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    navigationViewModel.navigateToCreateAccount()
                }
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Social Login Buttons
        SocialLoginButton(
            text = "Continue With Apple",
            icon = R.drawable.apple_logo,
            onClick = { /* Handle Apple login */ }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialLoginButton(
            text = "Continue With Google",
            icon = R.drawable.google_logo,
            onClick = { /* Handle Google login */ }
        )

        Spacer(modifier = Modifier.height(12.dp))

        SocialLoginButton(
            text = "Continue With Facebook",
            icon = R.drawable.facebook_logo,
            onClick = { /* Handle Facebook login */ }
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF5F5F5)
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = Color.Black,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}