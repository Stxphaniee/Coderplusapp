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


        Box(
            modifier = Modifier
                .fillMaxWidth(2f)
                .height(700.dp)
                .align(Alignment.TopCenter)
                .offset(y = 260.dp)
                .clip(RoundedCornerShape(percent = 40))
                .background(

                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color( 0xFF004482)),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
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


            Image(
                painter = painterResource(id = R.drawable.robot),
                contentDescription = "Bot",
                modifier = Modifier.size(250.dp)
            )


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
                fontWeight = FontWeight.Bold
            )


            Spacer(modifier = Modifier.height(32.dp))


            Button(
                onClick = onStartClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF043D72)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .width(250.dp)
                    .height(48.dp)
                    .shadow(5.dp, shape = RoundedCornerShape(50)),
            ) {
                Text(text = "¡Empieza ahora!",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }


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
                    .width(250.dp)
                    .height(48.dp)
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
