package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.QuestionList
import com.example.quizapp.repository.QuizRepository

class QuizViewModel : ViewModel() {
    var repository : QuizRepository = QuizRepository()
    lateinit var questionListLiveData: LiveData<QuestionList>

    init {
        questionListLiveData = repository.getQuestionsFromAPI()
    }

    fun getQuestionList():LiveData<QuestionList>{
        return questionListLiveData
    }
}