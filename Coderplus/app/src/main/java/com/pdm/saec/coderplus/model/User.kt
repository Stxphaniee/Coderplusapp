package com.pdm.saec.coderplus.model

data class User(
    val name: String,
    val age: Int,
    val country: String,
    val isAdmin: Boolean = false,
    val currentLevel: Int = 1
)
