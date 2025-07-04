package com.pdm.saec.coderplus.data

data class RemoteQuizQuestion(
    val id: Int,
    val topic: String,
    val type: String,
    val difficulty: String,
    val question: QuestionObj,
    val answers: List<AnswerObj>
)

data class QuestionObj(val text: String)
data class AnswerObj(val answer: String, val correct: Boolean? = false)

data class QuizQuestionUI(
    val question: String,
    val options: List<String>,
    val answer: String
)
