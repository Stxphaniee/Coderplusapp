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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import com.pdm.saec.coderplus.viewmodel.QuizViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun QuizScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    var selectedAnswer by remember { mutableStateOf(-1) }
    var lives by remember { mutableStateOf(3) }
    var correctCount by remember { mutableStateOf(0) }

    // Efecto lanzado para cargar las preguntas al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchQuestions()
    }

    // Recolecta el estado de las preguntas y el índice actual del ViewModel
    val questions = viewModel.questions.collectAsState().value
    val currentIndex = viewModel.currentIndex.collectAsState().value

    // Si todas las preguntas han sido respondidas, navega a la pantalla de resultados
    if (currentIndex >= questions.size && questions.isNotEmpty()) {
        LaunchedEffect(true) {
            navController.navigate("${NavigationRoutes.QuizFinished}/$correctCount/${questions.size}")
        }
        return // Sale del composable para evitar renderizar contenido innecesario
    }

    // Obtiene la pregunta actual y sus opciones/respuesta correcta
    val currentQuestion = questions.getOrNull(currentIndex)
    val correctAnswerIndex = currentQuestion?.options?.indexOf(currentQuestion.answer) ?: -1
    val answers = currentQuestion?.options ?: listOf("Respuesta 1", "Respuesta 2", "Respuesta 3","Respuesta 4")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.White, Color(0xFF004482)) // Fondo Degradado Blanco a Azul Oscuro
                )
            )
            .padding(horizontal = 16.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        // Contenedor para el botón de flecha y el título "Nivel 5"
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón de flecha
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Volver",
                    modifier = Modifier.size(32.dp)
                )
            }
            // Título del nivel
            Text(
                "Nivel 5",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF333760),
                modifier = Modifier.weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(48.dp))
        }
        // Indicador de vidas
        Row(
            modifier = Modifier.padding(vertical = 20.dp)
        ) {
            repeat(3) { index ->
                val heartRes = if (index < lives) R.drawable.ic_heart else R.drawable.ic_broken_heart
                Icon(
                    painter = painterResource(id = heartRes),
                    contentDescription = "Vida",
                    modifier = Modifier.size(50.dp),
                    tint = Color.Red
                )
                Spacer(modifier = Modifier.width(4.dp))
            }
        }

        // Tarjeta de la pregunta
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(2.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(16.dp)
            ) {
                // Indicador de progreso de preguntas DENTRO del cuadro, arriba a la izquierda
                Text(
                    text = "Pregunta ${currentIndex + 1} / ${questions.size}",
                    color = Color(0xFF333760),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Adjusted to align the question text to the center vertically within the remaining space
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = currentQuestion?.question ?: "Cargando pregunta...",
                        fontSize = 18.sp,
                        color = Color(0xFF333760)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Botones de respuesta
        answers.forEachIndexed { index, answer ->
            val isCorrect = selectedAnswer == correctAnswerIndex
            val isWrong = selectedAnswer == index && selectedAnswer != correctAnswerIndex

            OutlinedButton(
                onClick = {
                    if (selectedAnswer == -1 && currentQuestion != null) {
                        selectedAnswer = index
                        if (index == correctAnswerIndex) {
                            correctCount++
                        } else {
                            lives--
                            if (lives <= 0) {
                                navController.navigate("${NavigationRoutes.QuizFinished}/$correctCount/${questions.size}")
                                return@OutlinedButton
                            }
                        }
                        coroutineScope.launch {
                            delay(1000)
                            selectedAnswer = -1
                            viewModel.nextQuestion()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = when {
                        selectedAnswer == index && isCorrect -> Color(0xFF81C784)
                        selectedAnswer == index && isWrong -> Color(0xFFEF9A9A)
                        else -> Color(0xFF333661)
                    },
                    contentColor = Color.White
                )
            ) {
                // --------------- AÑADIDO: Fila para el Radio Button y el texto
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start // Alinea el contenido al inicio
                ) {
                    RadioButton( // --------------- AÑADIDO: El Radio Button
                        selected = (selectedAnswer == index), // Es seleccionado si 'selectedAnswer' coincide con el índice actual
                        onClick = {
                            // La lógica de selección se maneja en el onClick del OutlinedButton principal,
                            // pero el RadioButton necesita su propio onClick si se quiere que sea interactivo.
                            // Sin embargo, para que el RadioButton responda al toque del botón completo,
                            // a menudo se deja su onClick vacío o se delega al padre.
                            // En este caso, ya tenemos la lógica en el onClick del OutlinedButton.
                        },
                        colors = RadioButtonDefaults.colors( // --------------- AÑADIDO: Personaliza los colores del Radio Button
                            selectedColor = Color.White, // Color del círculo cuando está seleccionado
                            unselectedColor = Color.White, // Color del círculo cuando no está seleccionado
                            disabledSelectedColor = Color.Gray, // Color si está deshabilitado y seleccionado
                            disabledUnselectedColor = Color.Gray // Color si está deshabilitado y no seleccionado
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp)) // --------------- AÑADIDO: Espacio entre el Radio Button y el texto
                    Text(text = answer, fontSize = 16.sp)
                }
            }
        }
    }
}