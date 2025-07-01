package com.pdm.saec.coderplus.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun DeleteAccountScreen(
    onAccountDeleted: () -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    var isDeleting by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text("¿Estás seguro?") },
        text = { Text("Esta acción eliminará tu cuenta y tus datos permanentemente.") },
        confirmButton = {
            Button(
                onClick = {
                    val user = FirebaseAuth.getInstance().currentUser
                    val uid = user?.uid
                    isDeleting = true

                    if (uid != null) {
                        val db = FirebaseFirestore.getInstance()
                        db.collection("users").document(uid).delete().addOnCompleteListener {
                            user.delete().addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(context, "Cuenta eliminada", Toast.LENGTH_SHORT).show()
                                    onAccountDeleted()
                                } else {
                                    Toast.makeText(context, "Error eliminando cuenta", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                },
                enabled = !isDeleting
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Cancelar")
            }
        }
    )
}
