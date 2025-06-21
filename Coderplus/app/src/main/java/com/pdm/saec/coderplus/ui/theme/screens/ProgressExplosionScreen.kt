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
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import kotlin.collections.listOf

@Composable
fun ProgressExplosionScreen(
    navController: NavController,
    onStartLesson: () -> Unit
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
    )  {
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
                text = "¿Te gustan los retos, eh?",
                fontSize = 20.sp,
                color = Color(0xFF0D47A1)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 8.dp,
                color = Color.White,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.95f)
            ) {
                Text(
                    text = "Tienes tiempo para responder tantas preguntas como puedas. Solo cuida muy bien tus vidas porque si pierdes los 3 corazones estás fuera.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333760),
                    modifier = Modifier.padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Image(
                painter = painterResource(id = R.drawable.robot_x),
                contentDescription = "Robot",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

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
    Spacer(modifier = Modifier.height(24.dp))
}