package com.example.quizapplication

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import java.util.*
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val correctAnswers = hashMapOf<String, Int>()
    private val choices = hashSetOf<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Quiz application"

        correctAnswers[getString(R.string.q1answer2)] = 50
        correctAnswers[getString(R.string.q2answer1)] = 25
        correctAnswers[getString(R.string.q2answer2)] = -25
        correctAnswers[getString(R.string.q2answer3)] = 25

        radio.setOnCheckedChangeListener { group, checkedId ->
            val answer = group.findViewById(checkedId) as RadioButton

            if (answer.text.toString() == getString(R.string.q1answer2)) choices.add(answer.text.toString())
            else choices.remove(getString(R.string.q1answer2))
        }

        checkBox1.setOnCheckedChangeListener { _, isChecked ->
            toggleAnswer(isChecked, R.string.q2answer1)
        }

        checkBox2.setOnCheckedChangeListener { _, isChecked ->
            toggleAnswer(isChecked, R.string.q2answer2)
        }

        checkBox3.setOnCheckedChangeListener { _, isChecked ->
            toggleAnswer(isChecked, R.string.q2answer3)
        }

        btn_submit.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("“Congratulations!")

            val current = LocalDateTime.now()

            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val formatted = current.format(formatter)

            builder.setMessage(
                """Submission date: $formatted Your achieved: ${choices.sumOf { c -> correctAnswers[c]!! }}""".trimIndent()
            )

            builder.setPositiveButton("Close") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        btn_reset.setOnClickListener {
            val checkedButton = findViewById<RadioButton>(radio.checkedRadioButtonId);
            if (checkedButton.isChecked) checkedButton.isChecked = false
            checkBox1.isChecked = false
            checkBox2.isChecked = false
            checkBox3.isChecked = false
            choices.clear()
        }
    }

    private fun toggleAnswer(isChecked: Boolean, ans: Int) {
        if (isChecked) choices.add(getString(ans))
        else choices.remove(getString(ans))
    }

}