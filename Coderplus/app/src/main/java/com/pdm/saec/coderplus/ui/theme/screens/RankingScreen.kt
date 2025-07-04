package com.pdm.saec.coderplus.ui.theme.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.model.PlayerRanking

@Composable
fun RankingScreen(
    navController: NavController,
    players: List<PlayerRanking>
) {
    if (players.isEmpty()) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE3F2FD), Color(0xFF90CAF9))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val top3 = players.take(3)
    val others = players.drop(3)

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
        Column {
            // Cabecera
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
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
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.weight(1f),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
                Spacer(modifier = Modifier.size(48.dp))
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = null,
                    tint = Color.DarkGray
                )
                Spacer(Modifier.width(4.dp))
                Text("23h 10m", fontSize = 14.sp, color = Color.DarkGray)
            }

            Spacer(Modifier.height(16.dp))

            Podium(top3)

            Spacer(Modifier.height(24.dp))

            OthersRanking(others)
        }
    }
}

@Composable
private fun Podium(top3: List<PlayerRanking>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Bottom
    ) {
        if (top3.size >= 3) {
            PodiumItem(top3[1], position = 2, height = 120.dp)
            Spacer(Modifier.width(16.dp))
            PodiumItem(top3[0], position = 1, height = 160.dp)
            Spacer(Modifier.width(16.dp))
            PodiumItem(top3[2], position = 3, height = 100.dp)
        }
    }
}


@Composable
private fun PodiumItem(
    player: PlayerRanking,
    position: Int,
    height: Dp
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
        ) {
            if (!player.avatarUrl.isNullOrBlank()) {
                AsyncImage(
                    model = player.avatarUrl,
                    contentDescription = "${player.name} avatar",
                    modifier = Modifier.matchParentSize()
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_camara),
                    contentDescription = "Sin avatar",
                    modifier = Modifier
                        .matchParentSize()
                        .padding(12.dp),
                    tint = Color.Gray
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        Text(
            text = player.name.substringBefore(" "),
            fontSize = 14.sp,
            color = Color(0xFF1C2B56),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = "${player.points} pts",
            fontSize = 12.sp,
            color = Color(0xFF333760)
        )

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(height)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF208AEA), Color(0xFF004482))
                    ),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                position.toString(),
                fontSize = 32.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun OthersRanking(players: List<PlayerRanking>) {
    Surface(
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        color = Color(0xFFD9D7E4),
        modifier = Modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(players.size) { index ->
                val player = players[index]
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "${index + 4}",
                            fontSize = 16.sp,
                            color = Color(0xFF1C2B56),
                            modifier = Modifier.width(24.dp)
                        )

                        Spacer(Modifier.width(8.dp))

                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        ) {
                            if (!player.avatarUrl.isNullOrBlank()) {
                                AsyncImage(
                                    model = player.avatarUrl,
                                    contentDescription = "${player.name} avatar",
                                    modifier = Modifier.matchParentSize()
                                )
                            } else {
                                Icon(
                                    painter = painterResource(R.drawable.ic_camara),
                                    contentDescription = "Sin avatar",
                                    modifier = Modifier
                                        .matchParentSize()
                                        .padding(8.dp),
                                    tint = Color.Gray
                                )
                            }
                        }

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text(
                                text = player.name.substringBefore(" "),
                                fontSize = 14.sp,
                                color = Color(0xFF1C2B56)
                            )
                            Text(
                                text = "${player.points} pts",
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}
