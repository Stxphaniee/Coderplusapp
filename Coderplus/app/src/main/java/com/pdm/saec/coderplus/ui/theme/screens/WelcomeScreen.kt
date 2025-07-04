package com.pdm.saec.coderplus.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdm.saec.coderplus.R

@SuppressLint("Range")
@Composable
fun WelcomeScreen(
    onEmailLogin: (email: String, password: String) -> Unit,
    onRegister: () -> Unit,
    onGoogleClick: () -> Unit,
    navController: NavController
) {
    LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF043D72), Color(0xFF549ADA))
                )
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(2f)
                .height(700.dp)
                .align(Alignment.TopCenter)
                .offset(y = 260.dp)
                .clip(RoundedCornerShape(percent = 40))
                .background(

                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0xFF004482)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp))


            Image(
                painter = painterResource(id = R.drawable.robot),
                contentDescription = "Bot",
                modifier = Modifier.size(250.dp)
            )


            Text(
                text = "Coder Plus",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.DarkGray,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )
            Text(
                text = "¡La forma más divertida, efectiva y GRATIS de aprender C++!",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Ingresa tu correo") },
                singleLine = true,
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp)
                    .background(Color.White, RoundedCornerShape(50)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Ingresa tu contraseña") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .width(300.dp)
                    .height(56.dp)
                    .background(Color.White, RoundedCornerShape(50)),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { onEmailLogin(email.trim(), password) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF043D72)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(300.dp)
                    .height(48.dp)
                    .shadow(5.dp, RoundedCornerShape(50)),
            ) {
                Text(
                    "¡Empieza ahora!",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Text("or", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = onRegister,
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C2B56)),
                modifier = Modifier
                    .width(300.dp)
                    .height(48.dp)
            ) {
                Text("Regístrate!", fontSize = 16.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = onGoogleClick,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier
                    .width(300.dp)
                    .height(48.dp)
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Regístrate con Google",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(
        onEmailLogin = { email, password -> },
        onRegister = {},
        onGoogleClick = {},
        navController = NavController(LocalContext.current)
    )
}