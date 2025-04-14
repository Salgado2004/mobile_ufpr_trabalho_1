package br.ufpr.mobile.trabalho1.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.mobile.trabalho1.R
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    var contador: Int = 0
    var resposta: Int = 0
    lateinit var viewContador: TextView
    lateinit var viewExpression: TextView
    lateinit var viewResposta: TextView
    lateinit var editAnswer: EditText
    lateinit var buttonVerify: Button
    lateinit var buttonNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewContador = findViewById(R.id.viewContador)
        viewExpression = findViewById(R.id.viewExpression)
        viewResposta = findViewById(R.id.viewResposta)
        editAnswer = findViewById(R.id.editAnswer)
        buttonVerify = findViewById(R.id.verifyButton)
        buttonNext = findViewById(R.id.nextButton)

        buttonNext.setOnClickListener {
            nextQuestion()
        }

        nextQuestion()
    }

    @SuppressLint("SetTextI18n")
    fun nextQuestion() {
        contador++
        if (contador < 6) {
            val operators = listOf("+", "-")
            val num1 = Random.nextInt(0, 100)
            var num2: Int
            do {
                num2 = Random.nextInt(0, 100)
            } while (num2 > num1)
            val operador = operators.random()
            resposta = if (operators.indexOf(operador) == 0) num1 + num2 else num1 - num2
            viewExpression.text = "$num1 $operador $num2"
            viewContador.text = contador.toString()
            buttonVerify.isEnabled = true
            viewResposta.visibility = View.INVISIBLE
        } else {
            buttonNext.isEnabled = false
        }
    }

    fun verifyAnswer(view: View){
        try {
            val answer = editAnswer.text.toString().toInt()
            if (answer == resposta){
                viewResposta.text = "Correto!"
            } else {
                viewResposta.text = "Incorreto!"
            }
            viewResposta.visibility = View.VISIBLE
            buttonVerify.isEnabled = false
            buttonNext.isEnabled = true
        } catch (ex: NumberFormatException){
            Toast.makeText(this, "Insira um número válido!", Toast.LENGTH_SHORT).show()
        }
    }
}