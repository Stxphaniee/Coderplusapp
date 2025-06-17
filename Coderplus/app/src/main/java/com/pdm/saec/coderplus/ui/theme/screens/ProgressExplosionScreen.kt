package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pdm.saec.coderplus.R

@Composable
fun ProgressExplosionScreen(
    onStartLesson: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.explosion), // Usa una imagen que tengas o reemplaza
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "¡Estás por comenzar un nuevo reto!")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onStartLesson) {
            Text(text = "Iniciar Reto")
        }
    }
}
