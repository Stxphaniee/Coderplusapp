package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import com.pdm.saec.coderplus.viewmodel.MainViewModel

@Composable
fun Registro(
    navController: NavHostController,
    viewModel: MainViewModel = viewModel(),
    onCancel: () -> Unit
) {
    var name      by remember { mutableStateOf("") }
    var age       by remember { mutableStateOf("") }
    var country   by remember { mutableStateOf("") }
    var email     by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMsg  by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.White, Color(0xFF004482))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Registro",
            fontSize = 34.sp,
            color = Color(0xFF004482),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        @Composable
        fun LabeledInputField(
            label: String,
            value: String,
            onValueChange: (String) -> Unit,
            keyboardType: KeyboardType = KeyboardType.Text,
            visualTransformation: VisualTransformation = VisualTransformation.None
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                // label flotante
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(bottom = 4.dp),
                    shape = RoundedCornerShape(percent = 50),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        color = Color(0xFF333760),
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }

                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor   = Color(0xFF333760),
                        unfocusedContainerColor = Color(0xFF333760),
                        cursorColor             = Color.White,
                        focusedTextColor        = Color.White,
                        unfocusedTextColor      = Color.White,
                        focusedBorderColor      = Color.Transparent,
                        unfocusedBorderColor    = Color.Transparent
                    ),
                    shape = RoundedCornerShape(percent = 50),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    visualTransformation = visualTransformation
                )

                Spacer(Modifier.height(16.dp))
            }
        }

        LabeledInputField("Nombre:",     name,     { name = it })
        LabeledInputField("Edad:",       age,      { if (it.all(Char::isDigit)) age = it }, keyboardType = KeyboardType.Number)
        LabeledInputField("País:",       country,  { country = it })
        LabeledInputField("Correo:",     email,    { email = it },     keyboardType = KeyboardType.Email)
        LabeledInputField(
            label = "Contraseña:",
            value = password,
            onValueChange = { password = it },
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                errorMsg = null
                viewModel.registerWithEmail(
                    name, age, country, email, password
                ) { success, error ->
                    isLoading = false
                    if (success) {
                        navController.navigate(NavigationRoutes.Levels) {
                            popUpTo(NavigationRoutes.Welcome) { inclusive = true }
                        }
                    } else {
                        errorMsg = error ?: "Error inesperado"
                    }
                }
            },
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Aceptar", color = Color(0xFF333760), fontSize = 20.sp)
            }
        }

        errorMsg?.let {
            Spacer(Modifier.height(8.dp))
            Text(it, color = Color.Red)
        }

        Spacer(Modifier.height(16.dp))

        TextButton(onClick = onCancel) {
            Text("Cancelar", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistroPreview() {
    Registro(
        navController = rememberNavController(),
        viewModel = viewModel(),
        onCancel = {}
    )
}
