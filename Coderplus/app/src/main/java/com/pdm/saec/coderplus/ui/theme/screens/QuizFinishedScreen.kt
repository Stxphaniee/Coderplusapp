package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
            val currentLevel = viewModel.currentUser?.currentLevel ?: 1
            val passed = correctAnswers >= (totalQuestions + 1) / 2
            val unlocked = if (passed) currentLevel + 1 else currentLevel

            if (passed) {
                val gained = 100 + correctAnswers * 50
                viewModel.addPoints(gained)
                viewModel.updateUserLevel(unlocked)
            }
            navController.navigate(NavigationRoutes.Levels) {
                popUpTo(NavigationRoutes.Welcome) { inclusive = false }
            }
        }) {
            Text(text = "Volver a niveles")
        }
    }
}

