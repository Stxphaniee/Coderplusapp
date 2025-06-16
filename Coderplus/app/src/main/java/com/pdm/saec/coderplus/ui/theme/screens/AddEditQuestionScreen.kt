package com.pdm.saec.coderplus.ui.theme.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AddEditQuestionScreen(
    level: Int,
    existingQuestion: String? = null,
    existingOptions: List<String>? = null,
    correctIndex: Int? = null,
    onSave: (String, List<String>, Int) -> Unit,
    onCancel: () -> Unit
) {
    var question by remember { mutableStateOf(TextFieldValue(existingQuestion ?: "")) }
    val options = remember { mutableStateListOf("", "", "", "") }
    var selectedAnswer by remember { mutableStateOf(correctIndex ?: -1) }

    existingOptions?.let {
        for (i in it.indices) {
            if (i < options.size) options[i] = it[i]
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFFCE4EC), Color(0xFFD81B60))))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Editar Pregunta - Nivel $level",
            fontSize = 20.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Pregunta") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        repeat(4) { index ->
            OutlinedTextField(
                value = options[index],
                onValueChange = { options[index] = it },
                label = { Text("Opción ${index + 1}") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
        }

        Text("Selecciona la respuesta correcta", color = Color.White)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEachIndexed { index, _ ->
                OutlinedButton(
                    onClick = { selectedAnswer = index },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (selectedAnswer == index) Color.White else Color.Transparent,
                        contentColor = if (selectedAnswer == index) Color.Black else Color.White
                    )
                ) {
                    Text("Opción ${index + 1}")
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (question.text.isNotBlank() && selectedAnswer in 0..3) {
                    onSave(question.text, options, selectedAnswer)
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Guardar")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onCancel) {
            Text("Cancelar", color = Color.White)
        }
    }
}
