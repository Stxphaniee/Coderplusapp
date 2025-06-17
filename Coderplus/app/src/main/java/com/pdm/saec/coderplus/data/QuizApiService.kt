package com.pdm.saec.coderplus.data

import retrofit2.http.GET

interface QuizApiService {
    @GET("v1/cpp_questions?count=5&difficulty_easy=true&difficulty_medium=true&difficulty_hard=true&type_multiple_choice=true")
    suspend fun getQuestions(): List<QuizQuestion>
}
