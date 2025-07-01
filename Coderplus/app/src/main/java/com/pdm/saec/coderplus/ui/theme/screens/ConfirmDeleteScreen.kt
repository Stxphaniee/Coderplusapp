package com.pdm.saec.coderplus.ui.theme.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import kotlinx.coroutines.delay

@Composable
fun ConfirmDeleteScreen(navController: NavController) {
    LaunchedEffect(Unit) {
        delay(2000) // Espera 2 segundos
        navController.navigate(NavigationRoutes.Welcome) {
            popUpTo(0) // Limpia el backstack completo
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hasta pronto!!!! \nTu cuenta ha sido eliminada.",
            fontSize = 20.sp,
            lineHeight = 30.sp
        )
    }
}

