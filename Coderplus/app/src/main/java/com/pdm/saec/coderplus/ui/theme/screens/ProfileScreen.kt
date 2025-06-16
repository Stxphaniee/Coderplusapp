package com.pdm.saec.coderplus.ui.theme.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.model.User

@Composable
fun ProfileScreen(
    user: User,
    onEditProfile: () -> Unit,
    onDeleteAccount: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFBBDEFB), Color(0xFF1565C0))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.robot), // usa tu imagen de avatar
            contentDescription = "Avatar",
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = user.name,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = "Nivel ${user.currentLevel}",
            fontSize = 16.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onEditProfile,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
        ) {
            Text("Editar perfil", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = onDeleteAccount,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Text("Eliminar cuenta")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onLogout) {
            Text("Cerrar sesión", color = Color.White)
        }
    }
}
