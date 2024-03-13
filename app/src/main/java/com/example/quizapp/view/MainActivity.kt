package com.example.quizapp.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.model.Question
import com.example.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionList: List<Question>

    companion object{
        var correctAnswerCount = 0
        var questionCount = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        correctAnswerCount = 0;
        questionCount = 0;

        quizViewModel = ViewModelProvider(this).get(QuizViewModel::class.java)

        GlobalScope.launch (Dispatchers.Main){
            quizViewModel.getQuestionList().observe(this@MainActivity, Observer {
                if (it.size>0) {
                    questionList = it

                    binding.apply {
                        questionText.text = "Question 1: " + questionList!![0].question
                        radio1.text = questionList!![0].option_1
                        radio2.text = questionList!![0].option_2
                        radio3.text = questionList!![0].option_3
                        radio4.text = questionList!![0].option_4
                    }
                }
            })
        }

        var i = 1
        binding.apply {
            nextButton.setOnClickListener(View.OnClickListener {
                val selectedOptionId = radioGroup?.checkedRadioButtonId
                if (selectedOptionId != -1) {
                    val selectedOptionRadio = findViewById<View>(selectedOptionId!!) as RadioButton
                    questionList.let {
                        if (i < it.size!!) {
                            questionCount = it.size
                            if (selectedOptionRadio.text.toString().equals(it[i - 1].correct_option)) {
                                correctAnswerCount++
                                resultText?.text = "Current score: $correctAnswerCount"
                            }

                            questionText.text = "Question ${i + 1}: " + it[i].question
                            radio1.text = it[i].option_1
                            radio2.text = it[i].option_2
                            radio3.text = it[i].option_3
                            radio4.text = it[i].option_4
                        }

                        if (i == it.size!!.minus(1)) {
                            nextButton.text = "Finish"
                            radioGroup?.clearCheck()
                            i++
                        } else {
                            if (selectedOptionRadio.text.toString().equals(it[i-1].correct_option)) {
                                correctAnswerCount++
                                resultText?.text= "Correct Answer: $correctAnswerCount"
                            }

                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Please select an option", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}