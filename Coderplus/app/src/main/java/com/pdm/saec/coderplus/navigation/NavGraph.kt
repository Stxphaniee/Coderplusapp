package com.pdm.saec.coderplus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pdm.saec.coderplus.ui.theme.components.ConfirmDeleteDialog
import com.pdm.saec.coderplus.ui.theme.screens.*
import com.pdm.saec.coderplus.viewmodel.MainViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Welcome
    ) {
        composable(NavigationRoutes.Welcome) {
            WelcomeScreen(
                onStartClick = {
                    viewModel.loginAsRegularUser()
                    if (viewModel.currentUser?.isAdmin == true) {
                        navController.navigate(NavigationRoutes.AdminLevels)
                    } else {
                        navController.navigate(NavigationRoutes.ProgressExplosion)
                    }
                },
                onGoogleClick = {
                    viewModel.loginAsAdmin()
                    navController.navigate(NavigationRoutes.AdminLevels)
                },
                navController = navController
            )
        }

        composable(NavigationRoutes.ProgressExplosion) {
            ProgressExplosionScreen(
                onStartLesson = {
                    navController.navigate(NavigationRoutes.Quiz)
                }
            )
        }

        composable(NavigationRoutes.Quiz) {
            QuizScreen()
        }

        composable(NavigationRoutes.Profile) {
            val user = viewModel.currentUser
            if (user != null) {
                ProfileScreen(
                    user = user,
                    onEditProfile = {
                        navController.navigate(NavigationRoutes.EditProfile)
                    },
                    onDeleteAccount = {
                        navController.navigate(NavigationRoutes.ConfirmDelete)
                    },
                    onLogout = {
                        viewModel.currentUser = null
                        navController.popBackStack(NavigationRoutes.Welcome, inclusive = true)
                    }
                )
            } else {
                // si el usuario es null, vuelve a la pantalla de inicio
                navController.navigate(NavigationRoutes.Welcome)
            }
        }

        composable(NavigationRoutes.EditProfile) {
            val user = viewModel.currentUser
            if (user != null) {
                EditProfileScreen(
                    name = user.name,
                    age = user.age,
                    country = user.country,
                    onSave = { name, age, country ->
                        viewModel.currentUser = user.copy(name = name, age = age, country = country)
                        navController.popBackStack()
                    },
                    onCancel = {
                        navController.popBackStack()
                    }
                )
            } else {
                navController.navigate(NavigationRoutes.Welcome)
            }
        }

        composable(NavigationRoutes.ConfirmDelete) {
            ConfirmDeleteDialog(
                onConfirm = {
                    viewModel.currentUser = null
                    navController.navigate(NavigationRoutes.Welcome) {
                        popUpTo(0)
                    }
                },
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavigationRoutes.Ranking) {
            RankingScreen(
                rankingList = listOf(
                    PlayerRanking("Ana", 300),
                    PlayerRanking("Luis", 280),
                    PlayerRanking("Carlos", 250)
                )
            )
        }

        composable(NavigationRoutes.AdminLevels) {
            AdminLevelsScreen(
                onAddLevel = {
                    navController.navigate("${NavigationRoutes.AddEditQuestion}/0")
                },
                onEditLevel = { level ->
                    navController.navigate("${NavigationRoutes.AddEditQuestion}/$level")
                }
            )
        }




        composable(
            "${NavigationRoutes.AddEditQuestion}/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { backStackEntry ->
            val level = backStackEntry.arguments?.getInt("level") ?: 0
            AddEditQuestionScreen(
                level = level,
                onSave = { question, options, correctIndex ->
                    // guardar lógica
                    navController.popBackStack()
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }
    }
}
