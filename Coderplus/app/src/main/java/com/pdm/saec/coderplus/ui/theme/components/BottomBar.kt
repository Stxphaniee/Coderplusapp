package com.pdm.saec.coderplus.ui.theme.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.navigation.NavigationRoutes

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF1C2B56))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_fire),
            contentDescription = "Progreso",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    navController.navigate(NavigationRoutes.Ranking)
                }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_home),
            contentDescription = "Inicio",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    navController.navigate(NavigationRoutes.Levels)
                }
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_profile),
            contentDescription = "Perfil",
            tint = Color.White,
            modifier = Modifier
                .size(28.dp)
                .clickable {
                    navController.navigate(NavigationRoutes.Profile)
                }
        )
    }
}
