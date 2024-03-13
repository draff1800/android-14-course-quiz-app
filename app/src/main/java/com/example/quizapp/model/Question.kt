package com.example.quizapp.model

data class Question(
    val correct_option: String,
    val option_1: String,
    val option_2: String,
    val option_3: String,
    val option_4: String,
    val question: String
)