package com.pdm.saec.coderplus.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.pdm.saec.coderplus.data.AuthService
import com.pdm.saec.coderplus.model.User
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var currentUser by mutableStateOf<User?>(null)

    init {
        FirebaseAuth.getInstance().currentUser
            ?.let { onAuthSuccess(it) }
    }

    fun onAuthSuccess(firebaseUser: FirebaseUser) {
        viewModelScope.launch {
            val profile = AuthService.getUserProfile(firebaseUser.uid)
            currentUser = User(
                name         = profile.name.takeIf { it.isNotBlank() }
                    ?: firebaseUser.displayName.orEmpty()
                        .substringBefore("@"),
                age          = profile.age,
                country      = profile.country,
                isAdmin      = profile.isAdmin,
                currentLevel = profile.currentLevel,
                email        = firebaseUser.email.orEmpty(),
                password     = "",
                puntos       = profile.points,
                avatarUrl    = profile.avatarUrl
            )
        }
    }


    fun updateProfile(
        name: String,
        age: String,
        country: String,
        avatarUri: Uri?,
        onComplete: (Boolean, String?) -> Unit
    ) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return onComplete(false, "Usuario no autenticado")

        viewModelScope.launch {
            try {
                val updates = mutableMapOf<String, Any>(
                    "name" to name,
                    "age" to age,
                    "country" to country
                )
                if (avatarUri != null) {
                    val storageRef = FirebaseStorage.getInstance()
                        .reference
                        .child("avatars/$uid.jpg")
                    storageRef.putFile(avatarUri).await()
                    val url = storageRef.downloadUrl.await().toString()
                    updates["avatarUrl"] = url
                }
                AuthService.updateUserProfileMap(uid, updates)
                currentUser = currentUser?.copy(
                    name    = name,
                    age     = age,
                    country = country,
                )
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
        }
    }

    fun uploadAvatar(uri: Uri, onComplete: (Boolean, String?) -> Unit) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: run {
            onComplete(false, "No autenticado"); return
        }
        viewModelScope.launch {
            try {
                val ref = FirebaseStorage.getInstance()
                    .getReference("avatars/$uid.jpg")
                ref.putFile(uri).await()
                val url = ref.downloadUrl.await().toString()
                AuthService.updateUserAvatarUrl(uid, url)
                currentUser = currentUser?.copy(avatarUrl = url)
                onComplete(true, null)
            } catch (e: Exception) {
                onComplete(false, e.message)
            }
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

    fun addPoints(pointsToAdd: Int) {
        val user = currentUser ?: return
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val newTotal = user.puntos + pointsToAdd

        viewModelScope.launch {
            AuthService.updateUserPoints(uid, newTotal)
            currentUser = user.copy(puntos = newTotal)
        }
    }

    fun deleteAccount(onComplete: () -> Unit) {
        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                currentUser = null
                onComplete()
            } else {
                // Opcional: puedes añadir logs o feedback
            }
        }
    }
}
