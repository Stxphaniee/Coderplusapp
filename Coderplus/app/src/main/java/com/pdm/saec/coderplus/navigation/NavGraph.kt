package com.pdm.saec.coderplus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pdm.saec.coderplus.ui.theme.components.MainScaffold
import com.pdm.saec.coderplus.ui.theme.screens.*
import com.pdm.saec.coderplus.viewmodel.MainViewModel
import com.pdm.saec.coderplus.viewmodel.QuizViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    quizViewModel: QuizViewModel = QuizViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Welcome
    ) {
        composable(NavigationRoutes.Welcome) {
            WelcomeScreen(
                onStartClick = {
                    viewModel.loginAsRegularUser()
                    navController.navigate(NavigationRoutes.Levels)
                },
                onGoogleClick = {
                    viewModel.loginAsAdmin()
                    navController.navigate(NavigationRoutes.Levels)
                },
                navController = navController
            )
        }

        composable(NavigationRoutes.Levels) {
            MainScaffold(navController) { modifier ->
                LevelScreen(
                    navController = navController,
                    modifier = modifier,
                    viewModel = viewModel
                )
            }
        }

        composable(NavigationRoutes.Quiz) {
            QuizScreen(
                navController = navController,
                viewModel = quizViewModel
            )
        }

        composable(
            route = "${NavigationRoutes.QuizFinished}/{correctAnswers}/{totalQuestions}",
            arguments = listOf(
                navArgument("correctAnswers") { type = NavType.IntType },
                navArgument("totalQuestions") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val correctAnswers = backStackEntry.arguments?.getInt("correctAnswers") ?: 0
            val totalQuestions = backStackEntry.arguments?.getInt("totalQuestions") ?: 0

            QuizFinishedScreen(
                correctAnswers = correctAnswers,
                totalQuestions = totalQuestions,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}
