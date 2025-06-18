/*package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LockedLevelsScreen(
    userName: String = "Joaquin",
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFBFDDF6), Color(0xFF075B9A))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido $userName",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366),
                    shadow = Shadow(color = Color.Gray, offset = Offset(1f, 1f), blurRadius = 1f)
                )
            )
            Text(
                text = "¿Qué te gustaría aprender el día de hoy?",
                style = TextStyle(fontSize = 14.sp, color = Color(0xFF003366))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                PathBetweenLockedLevels()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelItem(level = "Nivel 6", isCompleted = false)
                    LevelItem(level = "Nivel 7", isCompleted = false)
                    LevelItem(level = "Nivel 8", isCompleted = false)
                    LevelItem(level = "Nivel 9", isCompleted = false)
                    LevelItem(level = "Nivel 10", isCompleted = false)
                }
            }
        }
    }
}

@Composable
fun PathBetweenLockedLevels() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val heightStep = size.height / 6

        val path = Path().apply {
            moveTo(width / 2, heightStep)
            cubicTo(width / 4, heightStep * 1.5f, width * 3 / 4, heightStep * 2f, width / 2, heightStep * 2.5f)
            cubicTo(width / 4, heightStep * 3f, width * 3 / 4, heightStep * 3.5f, width / 2, heightStep * 4f)
        }

        drawPath(path = path, color = Color.White, style = Stroke(width = 6f))
    }
}


 */