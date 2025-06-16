package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import com.pdm.saec.coderplus.R

@Composable
fun LevelScreen(userName: String = "Joaquin") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFBFDDF6), // celeste claro
                        Color(0xFF075B9A) // azul oscuro
                    )
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
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF003366)
                )
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
                    LevelItem(level = "Nivel 5", isCompleted = false, isNext = true)
                }
            }
        }

        BottomNavigationBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun LevelItem(level: String, isCompleted: Boolean, isNext: Boolean = false) {
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
                .background(Color(0xFF1C2B56), shape = CircleShape),
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

        drawPath(
            path = path,
            color = Color.White,
            style = Stroke(width = 6f)
        )
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1C2B56))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_fire),
            contentDescription = "Progreso",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Inicio",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Perfil",
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }
}
