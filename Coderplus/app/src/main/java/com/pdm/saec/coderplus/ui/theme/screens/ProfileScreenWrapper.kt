package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.model.User
import com.pdm.saec.coderplus.navigation.NavigationRoutes

@Composable
fun ProgressExplosionWithUserScreen(
    navController: NavController,
    onStartLesson: () -> Unit,
    user: User
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFFFFFFF), Color(0xFF004482))
                )
            )
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Explosión de progreso",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0D47A1)
            )

            Text(
                text = "¡Mira tus puntos acumulados!",
                fontSize = 20.sp,
                color = Color(0xFF0D47A1)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // CARD DE PUNTOS
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(6.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "Tus puntos actuales",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF1C2B56)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "${user.puntos} pts",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D47A1)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onStartLesson,
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .height(56.dp)
                    .width(220.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C2B56))
            ) {
                Text("Iniciar sesión", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }

        // Botón para ir al ranking
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .clickable {
                    navController.navigate(NavigationRoutes.Ranking)
                }
        ) {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = Color(0xFF1C2B56),
                shadowElevation = 6.dp
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_crown),
                    contentDescription = "Ir a Ranking",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(12.dp)
                        .size(50.dp)
                )
            }
        }
    }
}
