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

    val dataFieldBackgroundColor = Color(0xFF333760)
    val dataFieldContentColor = Color.White

    val labelCardBackgroundColor =
        Color.White
    val labelCardContentColor = Color(0xFF333760)

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


        Image(
            painter = painterResource(id = R.drawable.ic_camara),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(32.dp))


        @Composable
        fun LabeledInputField(
            label: String,
            value: String,
            onValueChange: (String) -> Unit,
            keyboardType: KeyboardType = KeyboardType.Text,
        ) {

            Card(
                modifier = Modifier
                    .width(100.dp)
                    .height(35.dp)
                    .align(Alignment.Start)
                    .offset(x = 16.dp),
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


            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .offset(y = (-5).dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = dataFieldBackgroundColor,
                    unfocusedContainerColor = dataFieldBackgroundColor,
                    disabledContainerColor = dataFieldBackgroundColor,
                    errorContainerColor = dataFieldBackgroundColor,

                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    disabledBorderColor = Color.LightGray,
                    errorBorderColor = Color.Red,

                    cursorColor = dataFieldContentColor,
                    focusedLabelColor = Color.Transparent,
                    unfocusedLabelColor = Color.Transparent,
                    focusedTextColor = dataFieldContentColor,
                    unfocusedTextColor = dataFieldContentColor
                ),
                shape = RoundedCornerShape(12.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        LabeledInputField(
            label = "Nombre:",
            value = name,
            onValueChange = { name = it }
        )


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


        LabeledInputField(
            label = "País:",
            value = country,
            onValueChange = { country = it }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
        }
    }
}

