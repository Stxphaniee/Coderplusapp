package com.pdm.saec.coderplus.ui.theme.components

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.unit.dp
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(NavigationRoutes.Levels) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Niveles") },
            label = { Text("Niveles") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(NavigationRoutes.Profile) },
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(NavigationRoutes.Ranking) },
            icon = { Icon(Icons.Default.Star, contentDescription = "Ranking") },
            label = { Text("Ranking") }
        )
    }
}
