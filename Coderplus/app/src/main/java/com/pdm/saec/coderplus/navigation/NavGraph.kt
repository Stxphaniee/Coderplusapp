package com.pdm.saec.coderplus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.pdm.saec.coderplus.ui.theme.components.ConfirmDeleteDialog
import com.pdm.saec.coderplus.ui.theme.components.MainScaffold
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
                    navController.navigate(NavigationRoutes.Levels)
                },
                onGoogleClick = {
                    navController.navigate(NavigationRoutes.Levels)
                },
                navController = navController
            )
        }

        composable(NavigationRoutes.Levels) {
            MainScaffold(navController) { modifier ->
                LevelScreen(navController = navController, modifier = modifier)
            }
        }

        composable(NavigationRoutes.LockedLevels) {
            MainScaffold(navController) { modifier ->
                LockedLevelsScreen(navController = navController, modifier = modifier)
            }
        }

        composable(NavigationRoutes.ProgressExplosion) {
            ProgressExplosionScreen(
                onStartLesson = {
                    navController.navigate(NavigationRoutes.Quiz)
                }
            )
        }

        composable(NavigationRoutes.Quiz) {
            MainScaffold(navController) { modifier ->
                QuizScreen(navController = navController, modifier = modifier)
            }
        }

        composable(NavigationRoutes.Profile) {
            val user = viewModel.currentUser
            if (user != null) {
                MainScaffold(navController) { modifier ->
                    ProfileScreen(
                        user = user,
                        modifier = modifier,
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
                }
            } else {
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
            MainScaffold(navController) { modifier ->
                RankingScreen(
                    navController = navController,
                    modifier = modifier,
                    rankingList = listOf(
                        PlayerRanking("Ana", 300),
                        PlayerRanking("Luis", 280),
                        PlayerRanking("Carlos", 250),
                        PlayerRanking("Paola", 240),
                        PlayerRanking("David", 230)
                    )
                )
            }
        }

        // Admin y edición de preguntas (opcional, no se elimina)
        // composable(NavigationRoutes.AdminLevels) { AdminLevelsScreen() }
        // composable(NavigationRoutes.AddEditQuestion) { AddEditQuestionScreen() }
    }
}
