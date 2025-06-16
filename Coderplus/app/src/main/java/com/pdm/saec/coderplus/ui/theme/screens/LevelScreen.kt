package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.navigation.NavigationRoutes

@Composable
fun LevelScreen(
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
                PathBetweenLevels()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LevelItem(level = "Nivel 1", isCompleted = true)
                    LevelItem(level = "Nivel 2", isCompleted = true)
                    LevelItem(level = "Nivel 3", isCompleted = true)
                    LevelItem(level = "Nivel 4", isCompleted = true)
                    LevelItem(
                        level = "Nivel 5",
                        isCompleted = false,
                        isNext = true,
                        onClick = { navController.navigate(NavigationRoutes.LockedLevels) }
                    )
                }
            }
        }
    }
}

@Composable
fun LevelItem(level: String, isCompleted: Boolean, isNext: Boolean = false, onClick: (() -> Unit)? = null) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = level,
            style = TextStyle(fontSize = 12.sp, color = Color.White),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .background(Color.White, shape = CircleShape)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )

        Box(
            modifier = Modifier
                .size(60.dp)
                .shadow(8.dp, shape = CircleShape)
                .background(Color(0xFF1C2B56), shape = CircleShape)
                .let { if (onClick != null) it.clickable { onClick() } else it },
            contentAlignment = Alignment.Center
        ) {
            val icon = when {
                isCompleted -> R.drawable.ic_check
                isNext -> R.drawable.ic_play
                else -> R.drawable.ic_lock
            }

            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun PathBetweenLevels() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width

        val path = Path().apply {
            moveTo(width / 2, 200f)
            cubicTo(width / 4, 300f, width * 3 / 4, 400f, width / 2, 500f)
            cubicTo(width / 4, 600f, width * 3 / 4, 700f, width / 2, 800f)
        }

        drawPath(path = path, color = Color.White, style = Stroke(width = 6f))
    }
}
