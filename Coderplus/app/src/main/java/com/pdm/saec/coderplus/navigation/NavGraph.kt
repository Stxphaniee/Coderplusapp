package com.pdm.saec.coderplus.navigation

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.pdm.saec.coderplus.model.User
import com.pdm.saec.coderplus.ui.theme.components.MainScaffold
import com.pdm.saec.coderplus.ui.theme.screens.ConfirmDeleteScreen
import com.pdm.saec.coderplus.ui.theme.screens.DeleteAccountScreen
import com.pdm.saec.coderplus.ui.theme.screens.EditProfileScreen
import com.pdm.saec.coderplus.ui.theme.screens.LevelScreen
import com.pdm.saec.coderplus.ui.theme.screens.ProfileScreen
import com.pdm.saec.coderplus.ui.theme.screens.ProgressExplosionScreen
import com.pdm.saec.coderplus.ui.theme.screens.QuizFinishedScreen
import com.pdm.saec.coderplus.ui.theme.screens.QuizScreen
import com.pdm.saec.coderplus.ui.theme.screens.RankingScreen
import com.pdm.saec.coderplus.ui.theme.screens.Registro
import com.pdm.saec.coderplus.ui.theme.screens.WelcomeScreen
import com.pdm.saec.coderplus.viewmodel.MainViewModel
import com.pdm.saec.coderplus.viewmodel.QuizViewModel
import com.pdm.saec.coderplus.viewmodel.RankingViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    onGoogleSignIn: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = NavigationRoutes.Welcome
    ) {
        composable(NavigationRoutes.Welcome) {
            val ctx = LocalContext.current
            WelcomeScreen(
                onEmailLogin = { email, pass ->
                    mainViewModel.loginWithEmail(email, pass) { success, error ->
                        if (success) {
                            navController.navigate(NavigationRoutes.Levels) {
                                popUpTo(NavigationRoutes.Welcome) { inclusive = true }
                            }
                        } else {
                            Toast.makeText(ctx, error ?: "Error desconocido", Toast.LENGTH_SHORT)
                                .show()
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
                LevelScreen(navController = navController, viewModel = mainViewModel)
            }
        }

        composable(
            route = "${NavigationRoutes.Quiz}/{level}",
            arguments = listOf(navArgument("level") { type = NavType.IntType })
        ) { back ->
            val lvl = back.arguments?.getInt("level") ?: 1
            val quizVm: QuizViewModel = viewModel(
                viewModelStoreOwner = back,
                modelClass = QuizViewModel::class.java
            )
            LaunchedEffect(lvl) { quizVm.fetchQuestions(lvl) }
            QuizScreen(navController = navController, level = lvl, viewModel = quizVm)
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
                viewModel = mainViewModel
            )
        }

        composable(NavigationRoutes.Profile) {
            val user = mainViewModel.currentUser

            MainScaffold(navController) { contentModifier ->
                if (user == null) {
                    Box(
                        modifier = contentModifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else {
                    Box(modifier = contentModifier.fillMaxSize()) {
                        ProfileScreen(
                            user = user,
                            onEditProfile = { navController.navigate(NavigationRoutes.EditProfile) },
                            onDeleteAccount = { navController.navigate(NavigationRoutes.ConfirmDelete) },
                            onLogout = {
                                mainViewModel.signOut()
                                navController.navigate(NavigationRoutes.Welcome) {
                                    popUpTo(0)
                                }
                            }
                        )
                    }
                }
            }
        }

        composable(NavigationRoutes.DeleteAccount) {
            DeleteAccountScreen(
                onAccountDeleted = {
                    mainViewModel.signOut()
                    navController.navigate(NavigationRoutes.ConfirmDelete) {
                        popUpTo(NavigationRoutes.DeleteAccount) { inclusive = true }
                    }
                },
                onCancel = {
                    navController.popBackStack()
                }
            )
        }


        composable(NavigationRoutes.ConfirmDelete) {
            ConfirmDeleteScreen(navController)
        }

        composable(NavigationRoutes.EditProfile) {
            MainScaffold(navController) { padding ->
                val user = mainViewModel.currentUser
                if (user == null) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    EditProfileScreen(
                        viewModel = mainViewModel,
                        currentUser = user,
                        onCancel = { navController.popBackStack() }
                    )
                }
            }
        }

        composable(NavigationRoutes.Ranking) {
            val rankingVm: RankingViewModel = viewModel()
            val players by rankingVm.players.collectAsState()
            RankingScreen(navController = navController, players = players)
        }

        composable(NavigationRoutes.ProgressExplosion) {
            MainScaffold(navController) {
                val user = mainViewModel.currentUser
                    ?: User(
                        name = "", age = "", country = "", isAdmin = false,
                        currentLevel = 1, puntos = 0, email = "", password = ""
                    )
                ProgressExplosionScreen(
                    navController = navController,
                    user = user,
                    onStartLesson = { navController.navigate("${NavigationRoutes.Quiz}/1") }
                )
            }
        }

        composable(NavigationRoutes.Registro) {
            Registro(
                navController = navController,
                viewModel = mainViewModel,
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
