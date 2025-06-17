package com.pdm.saec.coderplus.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://c-quiz-questions.p.rapidapi.com/"
    private const val API_KEY = "dc5fb35f21mshf4666d20e8c1958p1acad7jsn06791030f894"
    private const val API_HOST = "c-quiz-questions.p.rapidapi.com"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-RapidAPI-Key", API_KEY)
                .addHeader("X-RapidAPI-Host", API_HOST)
                .build()
            chain.proceed(request)
        }
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: QuizApiService = retrofit.create(QuizApiService::class.java)
}
