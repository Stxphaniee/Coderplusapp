package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.model.User

@Composable
fun EditProfileScreen(
    currentUser: User,
    onSave: (User) -> Unit,
    onCancel: () -> Unit,
) {
    var name by remember { mutableStateOf(currentUser.name) }
    var age by remember { mutableStateOf(currentUser.age.toString()) }
    var country by remember { mutableStateOf(currentUser.country) }

    // Colores base para los campos de entrada y las etiquetas de las Cards
    val dataFieldBackgroundColor = Color(0xFF333760) // Fondo de los campos de entrada
    val dataFieldContentColor = Color.White // Texto de los campos de entrada

    val labelCardBackgroundColor =
        Color.White // Fondo de las Cards de etiqueta (Nombre:, Edad:, País:)
    val labelCardContentColor = Color(0xFF333760) // Texto de las Cards de etiqueta

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    // Colores del degradado de fondo: Blanco arriba, Azul oscuro (004482) abajo
                    listOf(Color(0xFFFFFFFF), Color(0xFF004482))
                )
            )
            .padding(24.dp), // Padding general para la pantalla
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Texto "Editar Perfil"
        Text(
            text = "Editar Perfil",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004482) // Color del título
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Imagen de Avatar/Cámara
        Image(
            painter = painterResource(id = R.drawable.ic_camara), // Asegúrate de que esta imagen exista
            contentDescription = "Avatar",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio antes del primer campo de datos

        // Helper Composable para crear un bloque de etiqueta + campo de texto
        @Composable
        fun LabeledInputField(
            label: String,
            value: String,
            onValueChange: (String) -> Unit,
            keyboardType: KeyboardType = KeyboardType.Text,
        ) {
            // Etiqueta (Card blanca)
            Card(
                modifier = Modifier
                    .width(100.dp) // Ancho fijo para la etiqueta
                    .height(35.dp) // Altura de la etiqueta
                    .align(Alignment.Start) // Alineación a la izquierda dentro del Column
                    .offset(x = 16.dp), // Desplazar hacia la derecha
                shape = RoundedCornerShape(
                    topStart = 12.dp,
                    topEnd = 12.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                colors = CardDefaults.cardColors(containerColor = labelCardBackgroundColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = labelCardContentColor
                    )
                }
            }
            // Campo de texto editable (OutlinedTextField)
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp) // Padding horizontal para el campo
                    .offset(y = (-5).dp), // Ajuste vertical para conectar con la etiqueta
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = dataFieldBackgroundColor,
                    unfocusedContainerColor = dataFieldBackgroundColor,
                    disabledContainerColor = dataFieldBackgroundColor,
                    errorContainerColor = dataFieldBackgroundColor,

                    focusedBorderColor = Color.White, // Borde blanco
                    unfocusedBorderColor = Color.White, // Borde blanco
                    disabledBorderColor = Color.LightGray,
                    errorBorderColor = Color.Red,

                    cursorColor = dataFieldContentColor, // Cursor blanco
                    focusedLabelColor = Color.Transparent, // Ocultar etiqueta por defecto
                    unfocusedLabelColor = Color.Transparent, // Ocultar etiqueta por defecto
                    focusedTextColor = dataFieldContentColor, // Texto de entrada blanco
                    unfocusedTextColor = dataFieldContentColor
                ),
                shape = RoundedCornerShape(12.dp), // Esquinas redondeadas
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
            )
            Spacer(modifier = Modifier.height(16.dp)) // Espacio entre cada par de campo
        }

        // Uso del LabeledInputField para Nombre
        LabeledInputField(
            label = "Nombre:",
            value = name,
            onValueChange = { name = it }
        )

        // Uso del LabeledInputField para Edad
        LabeledInputField(
            label = "Edad:",
            value = age,
            onValueChange = { newAge ->
                if (newAge.all { it.isDigit() } || newAge.isEmpty()) {
                    age = newAge
                }
            },
            keyboardType = KeyboardType.Number
        )

        // Uso del LabeledInputField para País
        LabeledInputField(
            label = "País:",
            value = country,
            onValueChange = { country = it }
        )

        Spacer(modifier = Modifier.height(32.dp)) // Espacio antes de los botones Guardar/Cancelar

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
        }
    }
}

