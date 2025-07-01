package com.pdm.saec.coderplus.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
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
import android.widget.Toast

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
    onGoogleSignIn: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Welcome
    ) {

        composable(NavigationRoutes.Welcome) {
            val context = LocalContext.current
            WelcomeScreen(
                onEmailLogin = { email, pass ->
                    viewModel.loginWithEmail(email, pass) { success, error ->
                        if (success) {
                            navController.navigate(NavigationRoutes.Levels) {
                                popUpTo(NavigationRoutes.Welcome) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(
                                context,
                                error ?: "Error desconocido",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                },
                onRegister = { navController.navigate(NavigationRoutes.Registro) },
                onGoogleClick = onGoogleSignIn,
                navController = navController
            )
        }

        composable(NavigationRoutes.Levels) {
            MainScaffold(navController) {
                LevelScreen(
                    navController = navController,
                    viewModel = viewModel
                )
            }
        }

        composable(
            route = "${NavigationRoutes.Quiz}/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 1
            val quizVm: QuizViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                modelClass = QuizViewModel::class.java
            )
            LaunchedEffect(level) {
                quizVm.fetchQuestions(level)
            }
            QuizScreen(
                navController = navController,
                level = level,
                viewModel = quizVm
            )
        }

        composable(
            route = "${NavigationRoutes.QuizFinished}/{correctAnswers}/{totalQuestions}",
            arguments = listOf(
                navArgument("correctAnswers") { type = NavType.IntType },
                navArgument("totalQuestions") { type = NavType.IntType }
            )
        ) { back ->
            val correct = back.arguments?.getInt("correctAnswers") ?: 0
            val total = back.arguments?.getInt("totalQuestions") ?: 0
            QuizFinishedScreen(
                correctAnswers = correct,
                totalQuestions = total,
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(NavigationRoutes.Profile) {
            MainScaffold(navController) {
                val user = viewModel.currentUser ?: User(
                    name = "Invitado",
                    age = "",
                    country = "Desconocido",
                    isAdmin = false,
                    currentLevel = 1,
                    puntos = 0,
                    email = "",
                    password = ""
                )
                ProfileScreen(
                    user = user,
                    onEditProfile = { navController.navigate(NavigationRoutes.EditProfile) },
                    onDeleteAccount = {
                        viewModel.deleteAccount {
                            navController.navigate(NavigationRoutes.ConfirmDelete) {
                                popUpTo(NavigationRoutes.Profile) { inclusive = true }
                            }
                        }
                    },
                    onLogout = {
                        viewModel.signOut()
                        navController.navigate(NavigationRoutes.Welcome) {
                            popUpTo(NavigationRoutes.Welcome) { inclusive = true }
                        }
                    }
                )
            }
        }

        composable(NavigationRoutes.EditProfile) {
            MainScaffold(navController) {
                val user = viewModel.currentUser ?: return@MainScaffold
                EditProfileScreen(
                    currentUser = user,
                    onSave = {
                        viewModel.currentUser = it
                        navController.popBackStack()
                    },
                    onCancel = { navController.popBackStack() }
                )
            }
        }

        composable(NavigationRoutes.Ranking) {
            RankingScreen(navController)
        }

        composable(NavigationRoutes.ProgressExplosion) {
            MainScaffold(navController) {
                val user = viewModel.currentUser ?: User(
                    name = "Invitado",
                    age = "",
                    country = "Desconocido",
                    isAdmin = false,
                    currentLevel = 1,
                    puntos = 0,
                    email = "",
                    password = ""
                )
                ProgressExplosionScreen(
                    navController = navController,
                    user = user,
                    onStartLesson = {
                        navController.navigate("${NavigationRoutes.Quiz}/1")
                    }
                )
            }
        }

        composable(NavigationRoutes.Registro) {
            Registro(
                navController = navController,
                viewModel = viewModel,
                onCancel = { navController.popBackStack() }
            )
        }

        composable(NavigationRoutes.ConfirmDelete) {
            ConfirmDeleteScreen(navController)
        }
    }
}
