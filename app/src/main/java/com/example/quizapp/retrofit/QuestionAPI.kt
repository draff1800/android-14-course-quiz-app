package com.example.quizapp.retrofit

import com.example.quizapp.model.QuestionList
import retrofit2.Response
import retrofit2.http.GET;

interface QuestionAPI {
    @GET("questionapi.php")
    suspend fun getQuestions(): Response<QuestionList>
}