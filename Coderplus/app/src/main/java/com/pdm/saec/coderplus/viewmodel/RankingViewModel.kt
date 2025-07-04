package com.pdm.saec.coderplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.pdm.saec.coderplus.model.PlayerRanking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RankingViewModel : ViewModel() {
    private val _players = MutableStateFlow<List<PlayerRanking>>(emptyList())
    val players: StateFlow<List<PlayerRanking>> = _players

    init { fetchRanking() }

    private fun fetchRanking() {
        viewModelScope.launch {
            val snap = FirebaseFirestore.getInstance()
                .collection("users")
                .orderBy("points", Query.Direction.DESCENDING)
                .get()
                .await()
            val list = snap.documents.map { doc ->
                PlayerRanking(
                    name      = doc.getString("name").orEmpty(),
                    points    = doc.getLong("points")?.toInt() ?: 0,
                    avatarUrl = doc.getString("avatarUrl")
                )
            }
            _players.value = list
        }
    }
}
