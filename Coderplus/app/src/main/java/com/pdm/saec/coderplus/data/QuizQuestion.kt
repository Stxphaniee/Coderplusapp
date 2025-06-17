package com.pdm.saec.coderplus.data

data class QuizQuestion(
    val id: Int,
    val question: String,
    val answer: String,
    val options: List<String>
)
