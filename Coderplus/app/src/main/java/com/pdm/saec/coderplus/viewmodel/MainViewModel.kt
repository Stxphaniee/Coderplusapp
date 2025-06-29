package com.pdm.saec.coderplus.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.pdm.saec.coderplus.data.AuthService
import com.pdm.saec.coderplus.model.User
import kotlinx.coroutines.launch
class MainViewModel : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)

    fun onAuthSuccess(firebaseUser: FirebaseUser) {
        viewModelScope.launch {
            val profile = AuthService.getUserProfile(firebaseUser.uid)
            val nameFromFirestore = profile.name.takeIf { it.isNotBlank() }
            val nameFromGoogle    = firebaseUser.displayName?.takeIf { it.isNotBlank() }
            val fallbackEmailName = firebaseUser.email
                ?.substringBefore("@")
                ?.takeIf { it.isNotBlank() }

            val displayName = nameFromFirestore
                ?: nameFromGoogle
                ?: fallbackEmailName
                ?: "Invitado"

            currentUser = User(
                name         = displayName,
                age          = profile.age,
                country      = profile.country,
                isAdmin      = profile.isAdmin,
                currentLevel = profile.currentLevel,
                email        = firebaseUser.email.orEmpty(),
                password     = ""
            )
        }
    }

    fun updateUserLevel(newLevel: Int) {
        val user = currentUser ?: return
        if (newLevel > user.currentLevel) {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
            viewModelScope.launch {
                AuthService.updateUserLevel(uid, newLevel)
                currentUser = user.copy(currentLevel = newLevel)
            }
        }
    }

    fun registerWithEmail(
        name: String,
        age: String,
        country: String,
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val firebaseUser = AuthService.signUpWithEmail(email, password)
                AuthService.updateUserProfile(
                    uid     = firebaseUser.uid,
                    name    = name,
                    age     = age,
                    country = country
                )
                onAuthSuccess(firebaseUser)
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }

    fun signOut() {
        AuthService.signOut()
        currentUser = null
    }

    fun loginWithEmail(
        email: String,
        pass: String,
        onResult: (success: Boolean, errorMsg: String?) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val firebaseUser = AuthService.signInWithEmail(email, pass)
                onAuthSuccess(firebaseUser)
                onResult(true, null)
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }

   // fun loginAsRegularUser() { /* ... */ }
   // fun loginAsAdmin()       { /* ... */ }
}
