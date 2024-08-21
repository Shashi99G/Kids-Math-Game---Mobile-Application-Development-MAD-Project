package com.example.kidsmathsgame

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var TimeTextView: TextView? = null
    var ScoreTextView: TextView? = null
    var QuestionTextView: TextView? = null
    var AlertTextview: TextView? = null
    var FinalScoreTextView: TextView? = null
    var button0: Button? = null
    var button1: Button? = null
    var button2: Button? = null
    var button3: Button? = null
    var countDownTimer: CountDownTimer? = null
    var random: Random = Random.Default
    var a = 0
    var b = 0
    var indexOfCorrectAnswer = 0
    var answers = ArrayList<Int>()
    var points = 0
    var totalQuestions = 0
    var cals = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calInt = intent.getStringExtra("cals")
        cals = calInt ?: ""
        TimeTextView = findViewById(R.id.TimeTextView)
        ScoreTextView = findViewById(R.id.ScoreTextView)
        QuestionTextView = findViewById(R.id.QuestionTextView)
        AlertTextview = findViewById(R.id.AlertTextview)

        button0 = findViewById(R.id.button0)
        button1 = findViewById(R.id.button1)
        button2 = findViewById(R.id.button2)
        button3 = findViewById(R.id.button3)

        start()
    }

    fun NextQuestion(cal: String) {
        a = random.nextInt(10)
        b = random.nextInt(10)
        QuestionTextView?.text = "$a $cal $b"
        indexOfCorrectAnswer = random.nextInt(4)

        answers.clear()

        for (i in 0..3) {
            if (indexOfCorrectAnswer == i) {
                when (cal) {
                    "+" -> answers.add(a + b)
                    "-" -> answers.add(a - b)
                    "*" -> answers.add(a * b)
                    "/" -> {
                        try {
                            answers.add(a / b)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            } else {
                var wrongAnswer = random.nextInt(20)
                try {
                    while (
                        wrongAnswer == a + b
                        || wrongAnswer == a - b
                        || wrongAnswer == a * b
                        || wrongAnswer == a / b
                    ) {
                        wrongAnswer = random.nextInt(20)
                    }
                    answers.add(wrongAnswer)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        try {
            button0?.text = "${answers[0]}"
            button1?.text = "${answers[1]}"
            button2?.text = "${answers[2]}"
            button3?.text = "${answers[3]}"
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun optionSelect(view: View?) {
        totalQuestions++
        if (indexOfCorrectAnswer.toString() == view?.tag.toString()) {
            points++
            AlertTextview?.text = "Correct"
        } else {
            AlertTextview?.text = "Wrong"
        }
        ScoreTextView?.text = "$points/$totalQuestions"
        NextQuestion(cals)
    }

    fun playAgain(view: View?) {
        points = 0
        totalQuestions = 0
        ScoreTextView?.text = "$points/$totalQuestions"
        countDownTimer?.start()
    }

    private fun start() {
        NextQuestion(cals)
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(p0: Long) {
                TimeTextView?.text = "${p0 / 1000}5"
            }

            override fun onFinish() {
                TimeTextView?.text = "Time Up!"
                openDialog()
            }
        }.start()
    }

    private fun openDialog() {
        val inflate = LayoutInflater.from(this)
        val windialog = inflate.inflate(R.layout.win_layout, null)
        FinalScoreTextView = windialog.findViewById(R.id.FinalScoreTextView)
        val btnBack = windialog.findViewById<Button>(R.id.btnBack)
        val dialog = AlertDialog.Builder(this)
        dialog.setCancelable(false)
        dialog.setView(windialog)
        FinalScoreTextView?.text = "$points/$totalQuestions"
        btnPlayAgain.setOnClickListener { playAgain(it) }
        btnBack.setOnClickListener { onBackPressed() }
        val showDialog = dialog.create()
        showDialog.show()
    }
}
