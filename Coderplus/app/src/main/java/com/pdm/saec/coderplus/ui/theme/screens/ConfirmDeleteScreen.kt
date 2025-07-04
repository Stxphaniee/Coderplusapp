package com.pdm.saec.coderplus.ui.theme.screens


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
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


@Preview(showBackground = true)
@Composable
fun ConfirmDeleteScreenPreview() {
    val navController = rememberNavController()
    ConfirmDeleteScreen(navController = navController)
}
