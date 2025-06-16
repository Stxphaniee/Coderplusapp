package com.pdm.saec.coderplus.data

import retrofit2.http.GET
import retrofit2.http.Headers

interface QuizApiService {
    @Headers(
        "X-RapidAPI-Key: TU_API_KEY", // Reemplaza esto con tu clave real
        "X-RapidAPI-Host: c-quiz-questions.p.rapidapi.com"
    )
    @GET("questions")
    suspend fun getQuestions(): QuizResponse
}