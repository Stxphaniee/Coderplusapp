package com.pdm.saec.coderplus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.pdm.saec.coderplus.model.User

class MainViewModel : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)
        internal set

    fun loginAsRegularUser() {
        currentUser = User(
            name = "Joaquín",
            age = 25,
            country = "El Salvador",
            isAdmin = false,
            currentLevel = 5
        )
    }

    fun loginAsAdmin() {
        currentUser = User(
            name = "Admin",
            age = 30,
            country = "El Salvador",
            isAdmin = true,
            currentLevel = 10
        )
    }
}
