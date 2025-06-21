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


data class Player(val name: String, val points: Int, val avatar: Int)

@Composable
fun RankingScreen(navController: NavController) {

    val top3 = listOf(
        Player("Alejandro", 30, R.drawable.ic_npc1),
        Player("Roberto", 45, R.drawable.ic_npc2),
        Player("Joaquin", 25, R.drawable.ic_npc3)
    )


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


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFE3F2FD), Color(0xFF90CAF9))
                )
            )
            .padding(16.dp)
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Volver",
                        modifier = Modifier.size(32.dp)
                    )
                }

                Text(
                    "Ranking",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )

                Spacer(modifier = Modifier.size(48.dp))
            }




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

            Spacer(modifier = Modifier.height(8.dp))


            Podium(top3)

            Spacer(modifier = Modifier.height(16.dp))


            OthersRanking(others)
        }
    }
}

@Composable
fun Podium(top3: List<Player>) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {

        PodiumItem(player = top3[0], position = 2, height = 120.dp)
        PodiumItem(player = top3[1], position = 1, height = 160.dp)
        PodiumItem(player = top3[2], position = 3, height = 100.dp)
    }
}

@Composable
fun PodiumItem(player: Player, position: Int, height: Dp) {

    val podiumStartColor = Color(0xFF208AEA)
    val podiumEndColor = Color(0xFF004482)


    val blockWidth = 100.dp

    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painter = painterResource(id = player.avatar),
            contentDescription = "Avatar",
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(4.dp))


        Text(player.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)


        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .background(
                    color = Color(0xFF333760),
                    shape = RoundedCornerShape(25)
                )
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("${player.points} p", fontSize = 12.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(6.dp))


        Box(
            modifier = Modifier
                .width(blockWidth)
                .height(height)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(podiumStartColor, podiumEndColor)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                position.toString(),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp
            )
        }
    }
}

@Composable
fun OthersRanking(players: List<Player>) {

    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = Color(0xFFD9D7E4),
        modifier = Modifier
            .fillMaxWidth()
    ) {

        LazyColumn(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(players.size) { index ->
                val player = players[index]

                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
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
                        Spacer(modifier = Modifier.width(12.dp))
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