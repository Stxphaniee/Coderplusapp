package com.pdm.saec.coderplus.ui.theme.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.saec.coderplus.R

@Composable
fun ProgressExplosionScreen(
    onStartLesson: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFB3E5FC), Color(0xFF0D47A1))
                )
            )
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Explosión de progreso",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF001F54),
            textAlign = TextAlign.Center
        )

        Text(
            text = "¿Te gustan los retos, eh?",
            fontSize = 16.sp,
            color = Color(0xFF001F54),
            textAlign = TextAlign.Center
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.robot), // Usa tu imagen
                contentDescription = "Bot",
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Tienes tiempo para responder tantas\npreguntas como puedas. Solo cuida\nmuy bien tus vidas porque si pierdes\nlos 3 corazones estás fuera.",
                fontSize = 14.sp,
                color = Color.Black
            )
        }

        Button(
            onClick = onStartLesson,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D47A1)),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Iniciar lección", color = Color.White)
        }

        IconButton(onClick = { /* Ir a ranking u otra acción futura */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_crown), // ícono de corona si lo tienes
                contentDescription = "Ranking",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
