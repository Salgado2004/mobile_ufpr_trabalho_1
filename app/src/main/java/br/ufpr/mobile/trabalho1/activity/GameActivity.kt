package br.ufpr.mobile.trabalho1.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.mobile.trabalho1.R
import br.ufpr.mobile.trabalho1.User
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    var contador: Int = 0
    var resposta: Int = 0
    lateinit var jogador: User
    lateinit var tela: ConstraintLayout
    lateinit var viewContador: TextView
    lateinit var viewIntro: TextView
    lateinit var viewExpression: TextView
    lateinit var viewResposta: TextView
    lateinit var editAnswer: EditText
    lateinit var buttonVerify: Button
    lateinit var buttonNext: Button
    lateinit var buttonFinish: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle = intent.extras

        bundle?.let { dados ->
            val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                dados.getParcelable("user", User::class.java)
            } else {
                dados.getParcelable("user")
            }

            if (data != null) {
                jogador = data
            }

            tela = findViewById(R.id.main)
            viewContador = findViewById(R.id.viewContador)
            viewIntro = findViewById(R.id.viewIntro)
            viewExpression = findViewById(R.id.viewExpression)
            viewResposta = findViewById(R.id.viewResposta)
            editAnswer = findViewById(R.id.editAnswer)
            buttonVerify = findViewById(R.id.verifyButton)
            buttonNext = findViewById(R.id.nextButton)
            buttonFinish = findViewById(R.id.finishButton)

            viewIntro.text = "${jogador.userName}, responda corretamente a expressão abaixo"

            buttonNext.setOnClickListener {
                nextQuestion()
            }

            nextQuestion()
        } ?: throw RuntimeException("Intent está null")
    }

    @SuppressLint("SetTextI18n")
    fun nextQuestion() {
        contador++
        tela.background = ColorDrawable(ContextCompat.getColor(this, R.color.black))
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
            viewResposta.visibility = View.INVISIBLE
            viewExpression.visibility = View.INVISIBLE
            buttonNext.isEnabled = false
            buttonVerify.isEnabled = false
            buttonFinish.isEnabled = true
            buttonFinish.visibility = View.VISIBLE
        }
    }

    fun verifyAnswer(view: View) {
        try {
            val answer = editAnswer.text.toString().toInt()
            val color: ColorDrawable
            if (answer == resposta) {
                viewResposta.text = "Correto!"
                jogador.rights++
                color = ColorDrawable(ContextCompat.getColor(this, R.color.mygreen))
            } else {
                color = ColorDrawable(ContextCompat.getColor(this, R.color.myred))
                viewResposta.text = "Incorreto!"
            }
            tela.background = color
            viewResposta.visibility = View.VISIBLE
            buttonVerify.isEnabled = false
            buttonNext.isEnabled = true
            if (contador == 5) contador++
        } catch (ex: NumberFormatException) {
            Toast.makeText(this, "Insira um número válido!", Toast.LENGTH_SHORT).show()
        }
    }

    fun finish(view: View) {
        val intent = Intent(this, FinalActivity::class.java)
        intent.putExtra("user", jogador)
        startActivity(intent)
    }
}