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
import androidx.compose.ui.text.input.VisualTransformation
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

    val dataFieldBackgroundColor = Color(0xFF333760)
    val dataFieldContentColor = Color.White

    val labelTextColor = Color(0xFF333760)
    val labelCardBackgroundColor = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFFFFFFF), Color(0xFF004482))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Editar Perfil",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004482)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_camara),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            LabeledInputField(
                label = "Edad:",
                value = age,
                onValueChange = { newAge ->
                    if (newAge.all { it.isDigit() } || newAge.isEmpty()) {
                        age = newAge
                    }
                },
                modifier = Modifier.weight(1f),
                keyboardType = KeyboardType.Number,
                // Pasar colores aquí para que LabeledInputField pueda usarlos
                dataFieldBackgroundColor = dataFieldBackgroundColor,
                dataFieldContentColor = dataFieldContentColor,
                labelTextColor = labelTextColor,
                labelCardBackgroundColor = labelCardBackgroundColor
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        LabeledInputField(
            label = "Nombre:",
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            // Pasar colores aquí
            dataFieldBackgroundColor = dataFieldBackgroundColor,
            dataFieldContentColor = dataFieldContentColor,
            labelTextColor = labelTextColor,
            labelCardBackgroundColor = labelCardBackgroundColor
        )

        LabeledInputField(
            label = "País:",
            value = country,
            onValueChange = { country = it },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            // Pasar colores aquí
            dataFieldBackgroundColor = dataFieldBackgroundColor,
            dataFieldContentColor = dataFieldContentColor,
            labelTextColor = labelTextColor,
            labelCardBackgroundColor = labelCardBackgroundColor
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(end = 8.dp)
            ) {
                Text("Cancelar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Button(
                onClick = {
                    val updatedUser = currentUser.copy(
                        name = name,
                        age = age,
                        country = country
                    )
                    onSave(updatedUser)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004482)),
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(start = 8.dp)
            ) {
                Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }
    }
}

@Composable
fun LabeledInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    dataFieldBackgroundColor: Color,
    dataFieldContentColor: Color,
    labelTextColor: Color,
    labelCardBackgroundColor: Color
) {
    Column(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .wrapContentWidth(align = Alignment.Start)
                .padding(bottom = 4.dp),
            shape = RoundedCornerShape(percent = 50),
            colors = CardDefaults.cardColors(containerColor = labelCardBackgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = labelTextColor,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
            )
        }

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = dataFieldBackgroundColor,
                unfocusedContainerColor = dataFieldBackgroundColor,
                disabledContainerColor = dataFieldBackgroundColor,
                errorContainerColor = dataFieldBackgroundColor,

                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                disabledBorderColor = Color.Transparent,
                errorBorderColor = Color.Transparent,

                cursorColor = dataFieldContentColor,
                focusedLabelColor = Color.Transparent,
                unfocusedLabelColor = Color.Transparent,
                focusedTextColor = dataFieldContentColor,
                unfocusedTextColor = dataFieldContentColor
            ),
            shape = RoundedCornerShape(percent = 50),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            singleLine = true,
            visualTransformation = visualTransformation
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}