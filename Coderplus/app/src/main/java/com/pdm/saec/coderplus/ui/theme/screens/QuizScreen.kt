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
import com.pdm.saec.coderplus.ui.theme.components.BottomNavigationBar
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

    LaunchedEffect(Unit) {
        viewModel.fetchQuestions()
    }

    val questions = viewModel.questions.collectAsState().value
    val currentIndex = viewModel.currentIndex.collectAsState().value

    if (currentIndex >= questions.size && questions.isNotEmpty()) {
        LaunchedEffect(true) {
            navController.navigate("${NavigationRoutes.QuizFinished}/$correctCount/${questions.size}")
        }
        return
    }

    val currentQuestion = questions.getOrNull(currentIndex)
    val correctAnswerIndex = currentQuestion?.options?.indexOf(currentQuestion.answer) ?: -1
    val answers = currentQuestion?.options ?: listOf("Cargando...", "", "", "")

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

        Text("Pregunta ${currentIndex + 1} / ${questions.size}", color = Color.White)

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Text(
                text = currentQuestion?.question ?: "Cargando...",
                modifier = Modifier.padding(24.dp),
                fontSize = 18.sp
            )
        }

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
                    .padding(vertical = 4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = when {
                        selectedAnswer == index && isCorrect -> Color(0xFF81C784)
                        selectedAnswer == index && isWrong -> Color(0xFFEF9A9A)
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
