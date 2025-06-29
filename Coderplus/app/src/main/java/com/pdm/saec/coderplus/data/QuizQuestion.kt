package com.pdm.saec.coderplus.data

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val answer: String,
    val level: Int
)