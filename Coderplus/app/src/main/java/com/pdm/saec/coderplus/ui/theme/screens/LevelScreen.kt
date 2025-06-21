package com.pdm.saec.coderplus.ui.theme.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
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


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun LevelScreen(
    userName: String = "Joaquín",
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel
) {
    val levels = listOf(
        LevelData(1, true, "Nivel 1"),
        LevelData(2, true, "Nivel 2"),
        LevelData(3, true, "Nivel 3"),
        LevelData(4, true, "Nivel 4"),
        LevelData(5, false, "Nivel 5", isNext = true),
        LevelData(6, false, "Nivel 6"),
        LevelData(7, false, "Nivel 7"),
        LevelData(8, false, "Nivel 8"),
        LevelData(9, false, "Nivel 9"),
        LevelData(10, false, "Nivel 10"),
        LevelData(11, false, "Nivel 11"),
        LevelData(12, false, "Nivel 12"),
        LevelData(13, false, "Nivel 13"),
        LevelData(14, false, "Nivel 14"),
        LevelData(15, false, "Nivel 15"),
        LevelData(16, false, "Nivel 16")
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFBFDDF6), Color(0xFF075B9A))
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Bienvenido $userName",
                style = TextStyle(
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF003366),
                    shadow = Shadow(color = Color.Gray, offset = Offset(1f, 1f), blurRadius = 1f)
                )
            )
            Text(
                text = "¿Qué te gustaría aprender el día de hoy?",
                style = TextStyle(fontSize = 17.sp, color = Color(0xFF003366))
            )

            Spacer(modifier = Modifier.height(16.dp))

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 40.dp)
            ) {
                val levelPositionsMap = remember { mutableStateOf(mutableMapOf<Int, Offset>()) }

                val levelItemSizeDp = 60.dp
                val levelItemSizePx = with(LocalDensity.current) { levelItemSizeDp.toPx() }

                Canvas(modifier = Modifier.matchParentSize()) {
                    val path = Path()
                    for (i in 0 until levels.size - 1) {
                        val startLevelPos = levelPositionsMap.value[i]
                        val endLevelPos = levelPositionsMap.value[i + 1]

                        if (startLevelPos != null && endLevelPos != null) {
                            val startPoint = Offset(startLevelPos.x, startLevelPos.y + levelItemSizePx / 2)
                            val endPoint = Offset(endLevelPos.x, endLevelPos.y - levelItemSizePx / 2)

                            path.moveTo(startPoint.x, startPoint.y)

                            val controlPoint1 = Offset(startPoint.x, startPoint.y + (endPoint.y - startPoint.y) * 0.3f)
                            val controlPoint2 = Offset(endPoint.x, endPoint.y - (endPoint.y - startPoint.y) * 0.7f)

                            path.cubicTo(
                                x1 = controlPoint1.x, y1 = controlPoint1.y,
                                x2 = controlPoint2.x, y2 = controlPoint2.y,
                                x3 = endPoint.x, y3 = endPoint.y
                            )
                        }
                    }

                    drawPath(
                        path = path,
                        color = Color.White.copy(alpha = 0.7f),
                        style = Stroke(
                            width = 12f,
                            cap = StrokeCap.Round
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(50.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    levels.forEachIndexed { index, level ->
                        val itemAlignment = when (index % 4) {
                            0 -> Alignment.CenterHorizontally
                            1 -> Alignment.End
                            2 -> Alignment.CenterHorizontally
                            3 -> Alignment.Start
                            else -> Alignment.CenterHorizontally
                        }

                        LevelItem(
                            levelNumber = level.number,
                            isCompleted = level.isCompleted,
                            isNext = level.isNext,
                            levelName = level.name,
                            modifier = Modifier
                                .align(itemAlignment)
                                .onGloballyPositioned { coordinates ->
                                    val centerX = coordinates.positionInParent().x + coordinates.size.width / 2f
                                    val centerY = coordinates.positionInParent().y + coordinates.size.height / 2f
                                    levelPositionsMap.value[index] = Offset(centerX, centerY)
                                },
                            onClick = {
                                if (level.isNext) {
                                    navController.navigate(NavigationRoutes.Quiz)
                                }
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
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
    val cornerRadius = 25.dp
    val levelBoxSize = 80.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = levelName,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(0xFF1C2B56),
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .background(Color(0xFFFFFFFF), shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )

        Box(
            modifier = Modifier
                .size(levelBoxSize)
                .shadow(8.dp, shape = RoundedCornerShape(cornerRadius))
                .background(Color(0xFF1C2B56), shape = RoundedCornerShape(cornerRadius))
                .let { if (onClick != null) it.clickable { onClick() } else it },
            contentAlignment = Alignment.Center
        ) {
            val iconResourceId = when {
                isCompleted -> R.drawable.ic_check
                isNext -> R.drawable.ic_play
                else -> R.drawable.ic_lock
            }

            Image(
                painter = painterResource(id = iconResourceId),
                contentDescription = null,
                modifier = Modifier.size(45.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

data class LevelData(
    val number: Int,
    val isCompleted: Boolean,
    val name: String,
    val isNext: Boolean = false
)

@Composable
fun PathBetweenLevels() {
    // Eliminado
}