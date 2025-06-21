package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController // <------- Importar NavController

import com.pdm.saec.coderplus.R // <------- Asegúrate de que esta importación esté correcta

// <------- Clase de datos para representar a un jugador
data class Player(val name: String, val points: Int, val avatar: Int)

@Composable
fun RankingScreen(navController: NavController) { // <------- Se añade NavController como parámetro
    // <------- Lista de los 3 mejores jugadores
    val top3 = listOf(
        Player("Alejandro", 30, R.drawable.ic_npc1),
        Player("Roberto", 45, R.drawable.ic_npc2),
        Player("Joaquin", 25, R.drawable.ic_npc3)
    )

    // <------- Lista de otros jugadores para el resto del ranking
    val others = listOf(
        Player("Lupita", 24, R.drawable.ic_npc4),
        Player("Steven", 20, R.drawable.ic_npc5),
        Player("Rocio", 18, R.drawable.ic_npc1),
        Player("Carlos", 17, R.drawable.ic_npc7),
        Player("Karla", 16,
            R.drawable.ic_npc6
        ),
        Player("Rodolfo", 15, R.drawable.ic_npc1),
        Player("Kenia", 9, R.drawable.ic_npc4)
    )

    // <------- Contenedor principal con un degradado de fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE3F2FD), Color(0xFF90CAF9)) // <------- Degradado de azul claro a azul medio
                )
            )
            .padding(16.dp)
    ) {
        // <------- Columna principal que contiene todos los elementos de la pantalla
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            // <------- Contenedor para el botón de flecha y el título "Nivel 5" (según tu ejemplo)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween // <------- Distribuye el espacio entre los elementos
            ) {
                // <------- Botón de flecha para volver atrás
                IconButton(
                    onClick = {
                        navController.popBackStack() // <------- Llama a popBackStack para volver atrás
                    },
                    modifier = Modifier.size(48.dp) // <------- Tamaño del área clicable del botón
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back), // <------- ¡Aquí es donde debe existir el archivo ic_arrow_back!
                        contentDescription = "Volver",
                        modifier = Modifier.size(32.dp) // <------- Tamaño del icono de la flecha
                    )
                }
                // <------- Título del nivel
                Text(
                    "Ranking", // <------- Título "Nivel 5" según tu ejemplo
                    fontSize = 40.sp, // <------- Tamaño de fuente grande para el título
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1), // <------- Color de texto azul oscuro
                    modifier = Modifier
                        .weight(1f) // <------- Permite que el texto ocupe el espacio restante
                        .wrapContentWidth(Alignment.CenterHorizontally) // <------- Centra el texto horizontalmente en su espacio
                )
                // <------- Spacer para equilibrar el espacio del IconButton a la derecha
                Spacer(modifier = Modifier.size(48.dp))
            }

            // <------- Se elimina el Spacer superior original ya que la Row ahora maneja el espacio.

            // <------- Fila para mostrar el tiempo de actualización (ejemplo)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.DarkGray)
                Spacer(modifier = Modifier.width(4.dp))
                Text("23h 10m", fontSize = 14.sp, color = Color.DarkGray)
            }

            Spacer(modifier = Modifier.height(8.dp)) // <------- Espacio

            // <------- Muestra el podio con los 3 mejores jugadores
            Podium(top3)

            Spacer(modifier = Modifier.height(16.dp)) // <------- Espacio

            // <------- Muestra el resto de los jugadores en el ranking
            OthersRanking(others)
        }
    }
}

@Composable
fun Podium(top3: List<Player>) {
    // <------- Fila para organizar los elementos del podio
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center, // <------- Centra los elementos horizontalmente
        verticalAlignment = Alignment.Bottom // <------- Alinea los elementos en la parte inferior
    ) {
        // <------- Elementos del podio para el 2do, 1er y 3er lugar
        PodiumItem(player = top3[0], position = 2, height = 120.dp) // <------- Jugador en posición 2
        PodiumItem(player = top3[1], position = 1, height = 160.dp) // <------- Jugador en posición 1
        PodiumItem(player = top3[2], position = 3, height = 100.dp) // <------- Jugador en posición 3
    }
}

@Composable
fun PodiumItem(player: Player, position: Int, height: Dp) {
    // <------- Colores del degradado para los bloques del podio (SIN EFECTO 3D)
    val podiumStartColor = Color(0xFF208AEA) // <------- Azul claro del degradado
    val podiumEndColor = Color(0xFF004482)   // <------- Azul oscuro del degradado

    // <------- Ancho para los bloques del podio
    val blockWidth = 100.dp // <------- Mantenido el ancho

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // <------- Imagen del avatar del jugador
        Image(
            painter = painterResource(id = player.avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp)) // <------- Espacio

        // <------- Nombre del jugador
        Text(player.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)

        // <------- Contenedor para los puntos con el estilo de cuadrado redondeado
        Box(
            modifier = Modifier
                .padding(top = 4.dp) // <------- Espacio superior para separar del nombre
                .background(
                    color = Color(0xFF333760), // <------- Color de fondo #333760
                    shape = RoundedCornerShape(25) // <------- Bordes redondeados al 25%
                )
                .padding(horizontal = 8.dp, vertical = 4.dp), // <------- Relleno interno del Box
            contentAlignment = Alignment.Center // <------- Centra el texto dentro del Box
        ) {
            Text("${player.points} p", fontSize = 12.sp, color = Color.White) // <------- Texto de los puntos en blanco
        }

        Spacer(modifier = Modifier.height(6.dp)) // <------- Espacio restaurado aquí

        // <------- Contenedor principal del bloque del podio (PLANO, SIN EFECTO 3D)
        Box(
            modifier = Modifier
                .width(blockWidth) // <------- Ancho del bloque
                .height(height) // <------- Altura del bloque (sin sumar alturas de biseles 3D)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(podiumStartColor, podiumEndColor) // <------- Degradado azul del cuerpo
                    )
                ),
            contentAlignment = Alignment.Center // <------- Centra el número de posición
        ) {
            // <------- Número de posición dentro del bloque (GRANDE)
            Text(
                position.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp // <------- Tamaño de fuente grande para los números
            )
        }
    }
}

@Composable
fun OthersRanking(players: List<Player>) {
    // <------- Superficie para el resto del ranking con esquinas superiores redondeadas
    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = Color(0xFFD9D7E4), // <------- Color de fondo gris para el contenedor de la lista
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // <------- Columna perezosa para mostrar una lista desplazable de jugadores
        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp) // <------- Espacio entre elementos de la lista
        ) {
            items(players.size) { index ->
                val player = players[index]
                // <------- Tarjeta para cada jugador en la lista
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)), // <------- Color de fondo #D9D7E4 para las tarjetas individuales
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("${index + 4}", fontWeight = FontWeight.Bold, modifier = Modifier.width(24.dp))
                        Image(
                            painter = painterResource(id = player.avatar),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                        Spacer(modifier = Modifier.width(12.dp)) // <------- Espacio
                        Column {
                            Text(player.name, fontWeight = FontWeight.SemiBold)
                            Text("${player.points} preguntas", fontSize = 12.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}