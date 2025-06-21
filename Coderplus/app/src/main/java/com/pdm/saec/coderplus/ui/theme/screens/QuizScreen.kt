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
    val answers = currentQuestion?.options ?: listOf("Respuesta 1", "Respuesta 2", "Respuesta 3","Respuesta 4")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color.White, Color(0xFF004482))
                )
            )
            .padding(horizontal = 16.dp, vertical = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

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

                Text(
                    text = "Pregunta ${currentIndex + 1} / ${questions.size}",
                    color = Color(0xFF333760),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(8.dp))


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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = (selectedAnswer == index),
                        onClick = {





                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.White,
                            unselectedColor = Color.White,
                            disabledSelectedColor = Color.Gray,
                            disabledUnselectedColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = answer, fontSize = 16.sp)
                }
            }
        }
    }
}