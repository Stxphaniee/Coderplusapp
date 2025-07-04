package com.pdm.saec.coderplus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pdm.saec.coderplus.data.QuizQuestionUI
import com.pdm.saec.coderplus.data.RemoteQuizQuestion
import com.pdm.saec.coderplus.data.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel : ViewModel() {

    private val _questions = MutableStateFlow<List<QuizQuestionUI>>(emptyList())
    val questions: StateFlow<List<QuizQuestionUI>> = _questions

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex

    private val _correctCount = MutableStateFlow(0)
    fun fetchQuestions(level: Int) {
        viewModelScope.launch {
            try {
                val remoteList: List<RemoteQuizQuestion> = try {
                    RetrofitClient.apiService.getQuestions() ?: emptyList()
                } catch (e: Exception) {
                    e.printStackTrace()
                    emptyList()
                }
                val uiList = remoteList
                    .filter { it.question.text.isNotBlank() }
                    .mapNotNull { rq ->
                        val answers = rq.answers ?: return@mapNotNull null
                        if (answers.size < 2) return@mapNotNull null

                        val text = rq.question.text
                        val options = answers.mapNotNull { it.answer }
                        if (options.size < 2) return@mapNotNull null

                        val correct = answers.firstOrNull { it.correct == true }?.answer
                            ?: options.first()
                        QuizQuestionUI(
                            question = text,
                            options = options,
                            answer = correct
                        )
                    }
                    .shuffled()
                _questions.value = uiList
                _currentIndex.value = 0
                _correctCount.value = 0

            } catch (e: Exception) {
                e.printStackTrace()
                _questions.value = emptyList()
                _currentIndex.value = 0
                _correctCount.value = 0
            }
        }
    }

    fun nextQuestion() {
        if (_currentIndex.value < _questions.value.lastIndex) {
            _currentIndex.value += 1
        }
    }

}
