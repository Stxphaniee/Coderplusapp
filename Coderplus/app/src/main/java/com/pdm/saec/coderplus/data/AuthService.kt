package com.pdm.saec.coderplus.data

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

data class UserProfile(
    val email: String = "",
    val name: String = "",
    val age: String = "",
    val country: String = "",
    val currentLevel: Int = 1,
    val isAdmin: Boolean = false,
    val points: Int = 0,
    val avatarUrl: String? = null
)


object AuthService {
    private val auth = Firebase.auth
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun signInWithGoogle(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val result = auth.signInWithCredential(credential).await()
        val user = result.user!!
        ensureUserDoc(user.uid, user.email.orEmpty())
        return user
    }

    suspend fun signUpWithEmail(email: String, pass: String): FirebaseUser {
        val result = auth.createUserWithEmailAndPassword(email, pass).await()
        val user = result.user!!
        createUserProfile(user.uid, email)
        return user
    }

    suspend fun signInWithEmail(email: String, pass: String): FirebaseUser {
        val result = auth.signInWithEmailAndPassword(email, pass).await()
        val user = result.user!!
        ensureUserDoc(user.uid, email)
        return user
    }

    suspend fun updateUserProfileMap(uid: String, updates: Map<String, Any>) {
        firestore.collection("users")
            .document(uid)
            .set(updates, SetOptions.merge())
            .await()
    }

    fun signOut() = auth.signOut()

    private fun createUserProfile(uid: String, email: String) {
        val doc = firestore.collection("users").document(uid)
        doc.set(
            mapOf(
                "email" to email,
                "name" to "",
                "age" to "",
                "country" to "",
                "currentLevel" to 1,
                "points" to 0,
                "isAdmin" to false
            )
        )
    }

    suspend fun updateUserAvatarUrl(uid: String, avatarUrl: String) {
        firestore.collection("users")
            .document(uid)
            .update("avatarUrl", avatarUrl)
            .await()
    }

    private suspend fun ensureUserDoc(uid: String, email: String) {
        val ref = firestore.collection("users").document(uid)
        val snap = ref.get().await()
        if (!snap.exists()) {
            // perfil mínimo
            createUserProfile(uid, email)
        }
    }

    /** Crea o actualiza campos básicos de perfil tras registro */
    suspend fun updateUserProfile(
        uid: String,
        name: String,
        age: String,
        country: String
    ) {
        firestore.collection("users")
            .document(uid)
            .update(
                mapOf(
                    "name" to name,
                    "age" to age,
                    "country" to country
                )
            )
            .await()
    }

    suspend fun getUserProfile(uid: String): UserProfile {
        val snap = firestore.collection("users")
            .document(uid)
            .get()
            .await()
        return snap.toObject(UserProfile::class.java)
            ?: UserProfile() // si algo raro, devolvemos defaults
    }

    /** Actualiza solo el nivel, sin pisar otros campos */
    suspend fun updateUserLevel(uid: String, newLevel: Int) {
        firestore.collection("users")
            .document(uid)
            .set(
                _root_ide_package_.kotlin.collections.mapOf("currentLevel" to newLevel),
                SetOptions.merge()
            )
            .await()
    }

    /** Opcional: para guardar puntos totales */
    suspend fun updateUserPoints(uid: String, points: Int) {
        firestore.collection("users")
            .document(uid)
            .update("points", points)
            .await()
    }
}
