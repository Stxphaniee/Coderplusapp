package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.model.User

@Composable
fun ProfileScreen(
    user: User,
    onEditProfile: () -> Unit,
    onDeleteAccount: () -> Unit,
    onLogout: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        DeleteAccountScreen(
            onAccountDeleted = {
                showDeleteDialog = false
                onDeleteAccount()
            },
            onCancel = {
                showDeleteDialog = false
            }
        )
    }

    val shortName = user.name
        .split("\\s+".toRegex())
        .take(2)
        .joinToString(" ")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xFF004482))
                )
            )
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Perfil",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004482)
        )

        Text(
            text = "Nivel Actual: ${user.currentLevel}",
            fontSize = 20.sp,
            color = Color(0xFF004482)
        )

        Spacer(Modifier.height(16.dp))

        if (!user.avatarUrl.isNullOrBlank()) {
            AsyncImage(
                model = user.avatarUrl,
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(R.drawable.ic_camara),
                contentDescription = "Avatar por defecto",
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(25.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = shortName,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333760)
                )
            }
        }

        Spacer(Modifier.height(32.dp))

        val buttonSize = 140.dp

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onEditProfile,
                modifier = Modifier
                    .size(buttonSize)
                    .padding(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333760))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_avatar_de_usuario),
                        contentDescription = "Editar Perfil Icon",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Editar Perfil",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }

            Button(
                onClick = { showDeleteDialog = true },
                modifier = Modifier
                    .size(buttonSize)
                    .padding(4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333760))
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_eliminar),
                        contentDescription = "Borrar Datos Icon",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Borrar Datos",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier
                .size(buttonSize)
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF333760))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cerrar_sesion),
                    contentDescription = "Salir Icon",
                    modifier = Modifier.size(50.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Salir",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}
