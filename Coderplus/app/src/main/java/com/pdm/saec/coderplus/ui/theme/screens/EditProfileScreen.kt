package com.pdm.saec.coderplus.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.model.User
import com.pdm.saec.coderplus.viewmodel.MainViewModel


@Composable
fun EditProfileScreen(
    viewModel: MainViewModel,
    currentUser: User,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf(currentUser.name) }
    var age by remember { mutableStateOf(currentUser.age) }
    var country by remember { mutableStateOf(currentUser.country) }
    var isSaving by remember { mutableStateOf(false) }

    val dataFieldBg = Color(0xFF333760)
    val dataFieldFg = Color.White
    val labelFg     = Color(0xFF333760)
    val labelBg     = Color.White

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color.White, Color(0xFF004482))))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Editar Perfil",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004482)
        )
        Spacer(Modifier.height(16.dp))

        Row(
            Modifier.fillMaxWidth(),
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
            Spacer(Modifier.width(16.dp))
            LabeledInputField(
                label = "Edad:",
                value = age,
                onValueChange = { input ->
                    if (input.all(Char::isDigit) || input.isEmpty()) {
                        age = input
                    }
                },
                modifier = Modifier.weight(1f),
                keyboardType = KeyboardType.Number,
                dataFieldBackgroundColor = dataFieldBg,
                dataFieldContentColor    = dataFieldFg,
                labelTextColor           = labelFg,
                labelCardBackgroundColor = labelBg
            )
        }

        Spacer(Modifier.height(32.dp))

        LabeledInputField(
            label = "Nombre:",
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            dataFieldBackgroundColor = dataFieldBg,
            dataFieldContentColor    = dataFieldFg,
            labelTextColor           = labelFg,
            labelCardBackgroundColor = labelBg
        )

        LabeledInputField(
            label = "País:",
            value = country,
            onValueChange = { country = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            dataFieldBackgroundColor = dataFieldBg,
            dataFieldContentColor    = dataFieldFg,
            labelTextColor           = labelFg,
            labelCardBackgroundColor = labelBg
        )

        Spacer(Modifier.height(32.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = onCancel,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(end = 8.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Cancelar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            Button(
                onClick = {
                    isSaving = true
                    viewModel.updateProfile(
                        name    = name,
                        age     = age,
                        country = country
                    ) { success, errorMsg ->
                        isSaving = false
                        if (success) {
                            Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show()
                            onCancel()
                        } else {
                            Toast.makeText(context, errorMsg ?: "Error al guardar", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                enabled = !isSaving,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
                    .padding(start = 8.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004482))
            ) {
                if (isSaving) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
                } else {
                    Text("Guardar", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
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
    Column(modifier = modifier) {
        Card(
            shape = RoundedCornerShape(percent = 50),
            colors = CardDefaults.cardColors(containerColor = labelCardBackgroundColor),
            elevation = CardDefaults.cardElevation(2.dp)
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
            shape = RoundedCornerShape(percent = 50),
            singleLine = true,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = dataFieldBackgroundColor,
                unfocusedContainerColor = dataFieldBackgroundColor,
                disabledContainerColor = dataFieldBackgroundColor,
                errorContainerColor    = dataFieldBackgroundColor,
                focusedBorderColor     = Color.Transparent,
                unfocusedBorderColor   = Color.Transparent,
                disabledBorderColor    = Color.Transparent,
                errorBorderColor       = Color.Transparent,
                cursorColor            = dataFieldContentColor,
                focusedTextColor       = dataFieldContentColor,
                unfocusedTextColor     = dataFieldContentColor
            )
        )
        Spacer(Modifier.height(16.dp))
    }
}
