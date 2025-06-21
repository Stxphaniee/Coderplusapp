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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.navigation.NavigationRoutes

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = navBackStackEntry?.destination

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF333760))
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomBarItem(
            iconRes = R.drawable.ic_home,
            contentDescription = "Inicio",
            isSelected = currentDestination?.route == NavigationRoutes.Levels || currentDestination?.route == NavigationRoutes.LockedLevels,
            onClick = { navController.navigate(NavigationRoutes.Levels) }
        )
        BottomBarItem(
            iconRes = R.drawable.ic_fire,
            contentDescription = "Progreso",
            isSelected = currentDestination?.route == NavigationRoutes.Ranking,
            onClick = { navController.navigate(NavigationRoutes.Ranking) }
        )
        BottomBarItem(
            iconRes = R.drawable.ic_profile,
            contentDescription = "Perfil",
            isSelected = currentDestination?.route == NavigationRoutes.Profile,
            onClick = { navController.navigate(NavigationRoutes.Profile) }
        )
    }
}

@Composable
fun BottomBarItem(
    iconRes: Int,
    contentDescription: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Icon(
        painter = painterResource(id = iconRes),
        contentDescription = contentDescription,
        tint = if (isSelected) Color(0xFF333760) else Color.White,
        modifier = Modifier
            .size(28.dp)
            .clickable(onClick = onClick)
    )
}
