package com.pdm.saec.coderplus.data

data class QuizQuestionRaw(
    val id: kotlin.Int,
    val topic: kotlin.String,
    val type: kotlin.String,
    val difficulty: kotlin.String,
    val question: QuestionText,
    val answers: kotlin.collections.List<AnswerOption>
)

data class QuestionText(
    val text: kotlin.String
)

data class AnswerOption(
    val answer: kotlin.String,
    val correct: kotlin.Boolean? = false
)
