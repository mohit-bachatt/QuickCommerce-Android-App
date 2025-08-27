package com.example.peeko_android_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.peeko_android_app.navigation.NavigationViewModel
import com.example.peeko_android_app.ui.theme.Gray100
import com.example.peeko_android_app.ui.theme.Gray200
import com.example.peeko_android_app.ui.theme.PrimaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAccountScreen(
    navigationViewModel: NavigationViewModel
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Back Button
        IconButton(
            onClick = { navigationViewModel.navigateBack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Gray100, shape = RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Title
        Text(
            text = "Create Account",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(40.dp))

        // First Name Input Field
        OutlinedTextField(
            value = firstName,
            onValueChange = { firstName = it },
            placeholder = {
                Text(
                    text = "Firstname",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Last Name Input Field
        OutlinedTextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = {
                Text(
                    text = "Lastname",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp
            ),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBlue,
                unfocusedBorderColor = Color.Transparent,
                focusedContainerColor = Color(0xFFF5F5F5),
                unfocusedContainerColor = Color(0xFFF5F5F5)
            ),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

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
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Continue Button
        Button(
            onClick = {
                // Handle create account logic here
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

        // Forgot Password Link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Forgot Password ? ",
                color = Color.Black,
                fontSize = 14.sp
            )
            Text(
                text = "Reset",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    // Handle password reset
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}