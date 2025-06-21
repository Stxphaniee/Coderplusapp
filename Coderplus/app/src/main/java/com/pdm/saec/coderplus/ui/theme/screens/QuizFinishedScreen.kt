package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import com.pdm.saec.coderplus.viewmodel.MainViewModel

@Composable
fun QuizFinishedScreen(
    correctAnswers: Int,
    totalQuestions: Int,
    navController: NavHostController,
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "¡Reto finalizado!")
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Aciertos: $correctAnswers / $totalQuestions")

        Spacer(modifier = Modifier.height(32.dp))

        Button(onClick = {
            val current = viewModel.currentUser?.currentLevel ?: 1
            val unlocked = if (correctAnswers >= totalQuestions / 2) current + 1 else current
            viewModel.currentUser = viewModel.currentUser?.copy(currentLevel = unlocked)

            navController.navigate(NavigationRoutes.Levels) {
                popUpTo(NavigationRoutes.Levels) { inclusive = true }
            }
        }) {
            Text(text = "Volver a niveles")
        }
    }
}
