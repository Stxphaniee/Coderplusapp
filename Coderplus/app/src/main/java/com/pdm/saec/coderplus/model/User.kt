package com.pdm.saec.coderplus.model

data class User(
    val name: String,
    val age: String,
    val country: String,
    val isAdmin: Boolean = false,
    val currentLevel: Int = 1,
    val email: String,
    val password: String
)
