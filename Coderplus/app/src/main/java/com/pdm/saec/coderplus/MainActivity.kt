package com.pdm.saec.coderplus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.pdm.saec.coderplus.model.User
import com.pdm.saec.coderplus.navigation.NavGraph
import com.pdm.saec.coderplus.ui.theme.CoderplusTheme
import com.pdm.saec.coderplus.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usuario de prueba para evitar errores en perfil
        viewModel.currentUser = viewModel.currentUser ?: User(name = "Joaquin", age = 21, country = "El Salvador")

        setContent {
            CoderplusTheme {
                Surface(color = Color.White) {
                    val navController = rememberNavController()
                    NavGraph(navController = navController, viewModel = viewModel)
                }
            }
        }
    }
}
