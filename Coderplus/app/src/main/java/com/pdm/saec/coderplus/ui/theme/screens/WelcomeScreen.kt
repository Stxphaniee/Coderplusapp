package com.pdm.saec.coderplus.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.geometry.Offset
import com.pdm.saec.coderplus.R
import androidx.compose.ui.graphics.Brush

@SuppressLint("Range")
@Composable
fun WelcomeScreen(
    onStartClick: () -> Unit,
    onGoogleClick: () -> Unit,
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF043D72), Color(0xFF549ADA))
                )
            )
    ) {

        //Ovalo
        Box(
            modifier = Modifier
                .fillMaxWidth(2f) // Ocupa 1.5 veces el ancho del padre (la pantalla)
                .height(700.dp)
                .align(Alignment.TopCenter)
                .offset(y = 260.dp)
                .clip(RoundedCornerShape(percent = 40)) // Recorta con esquinas redondeadas al 50%
                .background(
                    // Aquí aplicamos el difuminado
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color( 0xFF004482)), // Tus dos colores: de abajo (0xFF003C90) hacia arriba (Blanco)
                        startY = 0f, // Empieza el gradiente desde la parte inferior del óvalo (0% de la altura)
                        endY = Float.POSITIVE_INFINITY // Termina el gradiente en la parte superior (100% de la altura)
                    )
                )
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            // Imagen del robot sin fondo
            Image(
                painter = painterResource(id = R.drawable.robot),
                contentDescription = "Bot",
                modifier = Modifier.size(250.dp)
            )

            // Título con sombra
            Text(
                text = "Coder Plus",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.DarkGray,
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                )
            )

            Text(
                text = "¡La forma más divertida, efectiva y GRATIS de aprender C++!",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp),
                fontWeight = FontWeight.Bold // Aquí se aplica la negrita
            )

            // Este es el elemento de la separacion
            Spacer(modifier = Modifier.height(32.dp)) // Crea un espacio vertical de 32dp

            // Botón azul
            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF043D72)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(250.dp) // <-- ¡Nuevo: Ancho específico! Puedes ajustar este valor.
                    .height(48.dp) // Mantienes la altura actual o la ajustas si lo deseas.
                    .shadow(5.dp, shape = RoundedCornerShape(50)),
            ) {
                Text(text = "¡Empieza Ahora!",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            // Separador
            Text("or",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )


            OutlinedButton(
                onClick = onGoogleClick,
                colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, Color.LightGray),
                modifier = Modifier
                    .width(250.dp) // <-- ¡Nuevo: Ancho específico! Puedes ajustar este valor.
                    .height(48.dp) // Mantienes la altura actual o la ajustas si lo deseas.
            ) {
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Regístrate con Google",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                    )
            }
        }
    }
}
