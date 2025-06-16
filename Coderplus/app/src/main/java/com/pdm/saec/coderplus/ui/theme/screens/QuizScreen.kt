package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.ui.theme.components.BottomNavigationBar

@Composable
fun QuizScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var selectedAnswer by remember { mutableStateOf(-1) }
    val correctAnswerIndex = 1
    var lives by remember { mutableStateOf(3) }

    val answers = listOf("Respuesta 1", "Respuesta 2", "Respuesta 3", "Respuesta 4")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFB3E5FC), Color(0xFF0D47A1))
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nivel 5", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)

        // Vidas
        Row {
            repeat(3) { index ->
                val heartRes = if (index < lives) R.drawable.ic_heart else R.drawable.ic_broken_heart
                Icon(
                    painter = painterResource(id = heartRes),
                    contentDescription = "Vida",
                    modifier = Modifier.size(32.dp),
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        // Número de pregunta
        Text(
            text = "8/10",
            color = Color.White,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )

        // Pregunta
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = "Pregunta del Nivel 5",
                modifier = Modifier.padding(24.dp),
                fontSize = 18.sp
            )
        }

        // Opciones
        answers.forEachIndexed { index, answer ->
            val isCorrect = selectedAnswer == correctAnswerIndex
            val isWrong = selectedAnswer == index && selectedAnswer != correctAnswerIndex

            OutlinedButton(
                onClick = {
                    if (selectedAnswer == -1) {
                        selectedAnswer = index
                        if (index != correctAnswerIndex) lives--
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = when {
                        selectedAnswer == index && isCorrect -> Color(0xFF81C784) // verde
                        selectedAnswer == index && isWrong -> Color(0xFFEF9A9A) // rojo
                        else -> Color(0xFF1A237E)
                    },
                    contentColor = Color.White
                )
            ) {
                Text(text = answer)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        BottomNavigationBar(navController = navController)
    }
}
