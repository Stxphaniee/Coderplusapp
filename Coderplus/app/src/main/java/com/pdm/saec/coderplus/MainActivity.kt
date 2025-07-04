package com.pdm.saec.coderplus

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.pdm.saec.coderplus.data.AuthService
import com.pdm.saec.coderplus.navigation.NavGraph
import com.pdm.saec.coderplus.navigation.NavigationRoutes
import com.pdm.saec.coderplus.ui.theme.CoderplusTheme
import com.pdm.saec.coderplus.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var navController: androidx.navigation.NavHostController
    private val googleSignInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            handleSignInResult(result.data)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, gso)
        setContent {
            CoderplusTheme {
                Surface(color = Color.White) {
                    navController = rememberNavController()
                    NavGraph(
                        navController   = navController,
                        mainViewModel   = viewModel,
                        onGoogleSignIn  = {
                            googleSignInLauncher.launch(googleClient.signInIntent)
                        }
                    )
                }
            }
        }

    }
    private fun handleSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            val idToken = account.idToken ?: return
            lifecycleScope.launch {
                val user = AuthService.signInWithGoogle(idToken)
                viewModel.onAuthSuccess(user)
                navController.navigate(NavigationRoutes.Levels) {
                    popUpTo(NavigationRoutes.Welcome) { inclusive = true }
                }
            }
        } catch (e: ApiException) {
        }
    }
}
