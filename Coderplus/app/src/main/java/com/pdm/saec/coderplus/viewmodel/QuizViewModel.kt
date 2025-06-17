package com.pdm.saec.coderplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.saec.coderplus.data.QuizQuestion
import com.pdm.saec.coderplus.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    // Lista de preguntas
    private val _questions = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val questions: StateFlow<List<QuizQuestion>> = _questions

    // Índice actual
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    // Conteo de respuestas correctas
    private val _correctCount = MutableStateFlow(0)
    val correctCount: StateFlow<Int> = _correctCount

    // Carga las preguntas desde la API
    fun fetchQuestions() {
        viewModelScope.launch {
            try {
                println("Cargando preguntas desde la API...")

                // Aquí se recibe una lista directamente
                val response = RetrofitClient.apiService.getQuestions()

                val filtered = response
                    .filter { it.question.isNotBlank() && it.options.size >= 2 }
                    .distinctBy { it.question }
                    .shuffled()

                println("Preguntas reales recibidas: ${filtered.size}")
                _questions.value = filtered
                _currentIndex.value = 0
                _correctCount.value = 0

            } catch (e: Exception) {
                println("Error al obtener preguntas: ${e.message}")
                e.printStackTrace()
            }
        }
    }

    // Avanza a la siguiente pregunta
    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.lastIndex) {
            _currentIndex.value += 1
        }
    }

    // Aumenta contador de respuestas correctas
    fun addCorrect() {
        _correctCount.value += 1
    }

    // Reinicia el quiz
    fun resetQuiz() {
        _currentIndex.value = 0
        _correctCount.value = 0
    }
}
