package com.pdm.saec.coderplus.ui.theme.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.model.PlayerRanking

@Composable
fun RankingScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    rankingList: List<PlayerRanking>
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Ranking General",
            style = MaterialTheme.typography.headlineSmall
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            itemsIndexed(rankingList) { index, player ->
                RankingItem(position = index + 1, player = player)
            }
        }
    }
}

@Composable
fun RankingItem(position: Int, player: PlayerRanking) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "$position°", modifier = Modifier.width(40.dp))
            Text(
                text = player.name,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "${player.score} pts",
                color = if (position == 1) Color(0xFFFFC107) else Color.Unspecified
            )
        }
    }
}
