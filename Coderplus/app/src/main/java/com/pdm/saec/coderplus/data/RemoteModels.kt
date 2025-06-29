package com.pdm.saec.coderplus.data

data class RemoteQuizQuestion(
    val id: kotlin.Int,
    val topic: kotlin.String,
    val type: kotlin.String,
    val difficulty: kotlin.String,
    val question: QuestionObj,
    val answers: kotlin.collections.List<AnswerObj>
)

data class QuestionObj(val text: kotlin.String)
data class AnswerObj(val answer: kotlin.String, val correct: kotlin.Boolean? = false)

data class QuizQuestionUI(
    val question: kotlin.String,
    val options: kotlin.collections.List<kotlin.String>,
    val answer: kotlin.String
)
