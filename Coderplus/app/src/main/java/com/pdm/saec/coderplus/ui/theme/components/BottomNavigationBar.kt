package com.pdm.saec.coderplus.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.navigation.NavigationRoutes

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val iconSize = 40.dp

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = Color(0xFF1C2B56),
        contentColor = Color.White,
        tonalElevation = 2.dp
    ) {

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate(route = NavigationRoutes.ProgressExplosion)},

            icon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_fire),
                    contentDescription = "Ranking",
                    modifier = Modifier.size(iconSize)
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
            onClick = { navController.navigate(NavigationRoutes.Levels) },
            icon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Inicio",
                    modifier = Modifier.size(iconSize)
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
                    painter = painterResource(id = R.drawable.ic_usuario),
                    contentDescription = "Perfil",
                    modifier = Modifier.size(iconSize)
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