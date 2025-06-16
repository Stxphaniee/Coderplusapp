package com.pdm.saec.coderplus.data

import com.pdm.saec.coderplus.data.RetrofitClient.GsonConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://c-quiz-questions.p.rapidapi.com/"

    val apiService: QuizApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuizApiService::class.java)
    }

    class Retrofit {

    }

    class GsonConverterFactory {
        companion object

    }
}

private fun GsonConverterFactory.Companion.create() {
    TODO("Not yet implemented")
}
