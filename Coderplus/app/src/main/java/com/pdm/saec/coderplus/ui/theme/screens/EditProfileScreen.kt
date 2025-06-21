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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.saec.coderplus.model.User

@Composable
fun EditProfileScreen(
    currentUser: User,
    onSave: (User) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(currentUser.name) }
    var age by remember { mutableStateOf(currentUser.age.toString()) }
    var country by remember { mutableStateOf(currentUser.country) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFBBDEFB), Color(0xFF1565C0))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Editar Perfil",
                color = Color.White,
                fontSize = 26.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Surface(
                shape = RoundedCornerShape(16.dp),
                tonalElevation = 8.dp,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = age,
                        onValueChange = { age = it },
                        label = { Text("Edad") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = country,
                        onValueChange = { country = it },
                        label = { Text("País") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                val updatedUser = currentUser.copy(
                                    name = name,
                                    age = age.toIntOrNull() ?: currentUser.age,
                                    country = country
                                )
                                onSave(updatedUser)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1565C0))
                        ) {
                            Text("Guardar", color = Color.White)
                        }

                        OutlinedButton(onClick = onCancel) {
                            Text("Cancelar")
                        }
                    }
                }
            }
        }
    }
}