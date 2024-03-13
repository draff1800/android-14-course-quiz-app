package com.example.quizapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapp.model.QuestionList
import com.example.quizapp.retrofit.QuestionAPI
import com.example.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {
    var questionAPI: QuestionAPI

    init {
        questionAPI = RetrofitInstance().getRetrofitInstance().create(QuestionAPI::class.java)
    }

    fun getQuestionsFromAPI():LiveData<QuestionList>{
        var data = MutableLiveData<QuestionList>()
        var questionList : QuestionList

        GlobalScope.launch(Dispatchers.IO) {
            val response = questionAPI.getQuestions()
            if (response != null){
                questionList = response.body()!!
                data.postValue(questionList)
            }
        }
        return data;
    }
}