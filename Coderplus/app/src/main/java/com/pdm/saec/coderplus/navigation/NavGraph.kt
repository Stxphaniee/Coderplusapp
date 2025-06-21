package com.pdm.saec.coderplus.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pdm.saec.coderplus.model.User
import com.pdm.saec.coderplus.ui.theme.components.MainScaffold
import com.pdm.saec.coderplus.ui.theme.screens.*
import com.pdm.saec.coderplus.viewmodel.MainViewModel
import com.pdm.saec.coderplus.viewmodel.QuizViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
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
            val quizViewModel: QuizViewModel = viewModel()
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


        composable(NavigationRoutes.Profile) {
            MainScaffold(navController) { modifier ->
                val user = viewModel.currentUser ?: User(
                    name = "Invitado",
                    age = 0,
                    country = "Desconocido",
                    isAdmin = false,
                    currentLevel = 0
                )

                ProfileScreen(
                    user = user,
                    onEditProfile = {
                        navController.navigate(NavigationRoutes.EditProfile)
                    },
                    onDeleteAccount = {
                        // TODO: ConfirmDeleteDialog
                    },
                    onLogout = {
                        navController.navigate(NavigationRoutes.Welcome) {
                            popUpTo(NavigationRoutes.Welcome) { inclusive = true }
                        }
                    },
                    modifier = modifier
                )
            }
        }


        composable(NavigationRoutes.EditProfile) {
            MainScaffold(navController) { modifier ->
                val user = viewModel.currentUser ?: return@MainScaffold
                EditProfileScreen(
                    currentUser = user,
                    onSave = {
                        viewModel.currentUser = it
                        navController.popBackStack()
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            }
        }


        composable(NavigationRoutes.Ranking) {
            RankingScreen(navController = navController)
        }

        composable(NavigationRoutes.ProgressExplosion) {
            MainScaffold(navController = navController) { modifier ->
                ProgressExplosionScreen(
                    navController = navController,
                    onStartLesson = {
                        navController.navigate(NavigationRoutes.Quiz)
                    }
                )

            }
        }
    }
}