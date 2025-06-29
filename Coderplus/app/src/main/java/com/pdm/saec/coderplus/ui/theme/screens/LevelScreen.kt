package com.pdm.saec.coderplus.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import com.pdm.saec.coderplus.R
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import com.pdm.saec.coderplus.viewmodel.MainViewModel
import com.pdm.saec.coderplus.viewmodel.QuizViewModel
import kotlinx.coroutines.launch

data class LevelData(
    val number: Int,
    val isCompleted: Boolean,
    val name: String,
    val isNext: Boolean = false
)

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LevelScreen(
    navController: NavHostController,
    viewModel: MainViewModel,
    quizViewModel: QuizViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val user = viewModel.currentUser
    val displayName = remember(user) {
        user?.name
            ?.substringBefore(" ")
            ?.takeIf { it.isNotBlank() }
            ?: user?.email?.substringBefore("@")
            ?: "Invitado"
    }
    val unlockedLevel = user?.currentLevel ?: 1

    val totalLevels = 16
    val levels = remember(unlockedLevel) {
        (1..totalLevels).map { lvl ->
            LevelData(
                number = lvl,
                isCompleted = lvl < unlockedLevel,
                isNext = lvl == unlockedLevel,
                name = "Nivel $lvl"
            )
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFBFDDF6), Color(0xFF075B9A))
                )
            )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Bienvenido $displayName",
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366),
                    shadow = Shadow(Color.Gray, Offset(1f,1f), 1f)
                )
            )
            Text(
                "¿Qué te gustaría aprender el día de hoy?",
                style = TextStyle(fontSize = 17.sp, color = Color(0xFF003366))
            )
            Spacer(Modifier.height(16.dp))

            BoxWithConstraints(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 40.dp)
            ) {
                val levelPositions = remember { mutableStateOf(mutableMapOf<Int, Offset>()) }
                val itemSizeDp = 80.dp
                val itemSizePx = with(LocalDensity.current) { itemSizeDp.toPx() }

                Canvas(Modifier.matchParentSize()) {
                    val path = Path()
                    levels.forEachIndexed { idx, _ ->
                        val start = levelPositions.value[idx]
                        val end = levelPositions.value[idx + 1]
                        if (start != null && end != null) {
                            val p1 = Offset(start.x, start.y + itemSizePx / 2)
                            val p4 = Offset(end.x, end.y - itemSizePx / 2)
                            path.moveTo(p1.x, p1.y)
                            val cp1 = Offset(p1.x, p1.y + (p4.y - p1.y) * 0.3f)
                            val cp2 = Offset(p4.x, p4.y - (p4.y - p1.y) * 0.3f)
                            path.cubicTo(cp1.x, cp1.y, cp2.x, cp2.y, p4.x, p4.y)
                        }
                    }
                    drawPath(
                        path = path,
                        color = Color.White.copy(alpha = 0.7f),
                        style = Stroke(width = 12f, cap = StrokeCap.Round)
                    )
                }

                Column(
                    Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val coroutine = rememberCoroutineScope()
                    levels.forEachIndexed { idx, level ->
                        val align = when (idx % 4) {
                            0 -> Alignment.CenterHorizontally
                            1 -> Alignment.End
                            2 -> Alignment.CenterHorizontally
                            3 -> Alignment.Start
                            else -> Alignment.CenterHorizontally
                        }
                        LevelItem(
                            levelNumber = level.number,
                            levelName = level.name,
                            isCompleted = level.isCompleted,
                            isNext = level.isNext,
                            modifier = Modifier
                                .align(align)
                                .onGloballyPositioned { coords ->
                                    val x = coords.positionInParent().x + coords.size.width / 2f
                                    val y = coords.positionInParent().y + coords.size.height / 2f
                                    levelPositions.value[idx] = Offset(x, y)
                                },
                            onClick = {
                                if (level.isNext) {
                                    coroutine.launch {
                                        quizViewModel.fetchQuestions(level.number)
                                        navController.navigate("${NavigationRoutes.Quiz}/${level.number}")
                                    }
                                }
                            }
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun LevelItem(
    levelNumber: Int,
    levelName: String,
    isCompleted: Boolean,
    isNext: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null
) {
    val radius = 25.dp
    val sizeDp = 80.dp
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            levelName,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF1C2B56),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(sizeDp)
                .shadow(8.dp, RoundedCornerShape(radius))
                .background(Color(0xFF1C2B56), RoundedCornerShape(radius))
                .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
        ) {
            val icon = when {
                isCompleted -> R.drawable.ic_check
                isNext -> R.drawable.ic_play
                else -> R.drawable.ic_lock
            }
            Image(
                painter = painterResource(icon),
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}
