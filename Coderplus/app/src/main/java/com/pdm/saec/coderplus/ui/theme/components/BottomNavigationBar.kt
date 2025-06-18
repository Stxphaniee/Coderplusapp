package com.pdm.saec.coderplus.ui.theme.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import androidx.compose.foundation.Image // Importar para usar Image
import androidx.compose.ui.res.painterResource // Importar para cargar recursos de imagen
import com.pdm.saec.coderplus.R // Importar la clase R para acceder a tus drawables
import androidx.compose.foundation.layout.size // Importar para el modificador size

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Define un tamaño común para todos los iconos
    val iconSize = 40.dp // Puedes ajustar este valor a tu preferencia

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFF1C2B56), // Un azul oscuro similar al de la imagen
        contentColor = Color.White, // Color del contenido (iconos y texto)
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(NavigationRoutes.Levels) },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_fire),
                    contentDescription = "Niveles",
                    modifier = Modifier.size(iconSize) // Aplicar el tamaño común aquí
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(NavigationRoutes.Profile) },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_home), // Asumiendo ic_home para Ranking
                    contentDescription = "Perfil",
                    modifier = Modifier.size(iconSize) // Aplicar el tamaño común aquí
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(NavigationRoutes.Ranking) },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_usuario),
                    contentDescription = "Ranking",
                    modifier = Modifier.size(iconSize) // Aplicar el tamaño común aquí
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                unselectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedTextColor = Color.White,
                indicatorColor = Color.Transparent
            )
        )
    }
}