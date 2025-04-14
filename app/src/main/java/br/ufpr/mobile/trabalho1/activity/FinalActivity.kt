package br.ufpr.mobile.trabalho1.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.mobile.trabalho1.MainActivity
import br.ufpr.mobile.trabalho1.R
import br.ufpr.mobile.trabalho1.User

class FinalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_final)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var nome = ""
        var rights = 0
        val bundle = intent.extras
        if (bundle != null) {
            val user = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable("user", User::class.java)
            } else {
                bundle.getParcelable("user")
            }
            if (user != null) {
                var nomeOutput = findViewById<TextView>(R.id.nameOutput)
                var notaOutput = findViewById<TextView>(R.id.notaOutput)
                var responseOutput = findViewById<TextView>(R.id.responseOutput)
                nome = user.userName
                rights = user.rights

                nomeOutput.text = getNome(rights, nome)
                notaOutput.text = getNota(rights)
                responseOutput.text = getResponse(rights)
            }
        }

        val restartButton = findViewById<Button>(R.id.restartButton)
        restartButton.setOnClickListener {
            goNextActivity()
        }
    }

    fun getNome(rights: Int, nome: String): String{
        return when(rights) {
            0 -> "Poxa, $nome..."
            1 -> "Olá, $nome"
            2 -> "Olá, $nome"
            3 -> "Bacana, $nome"
            4 -> "Boa $nome"
            else -> "Parabéns $nome!"
        }
    }

    fun getNota(rights: Int): String {
        return "Você tirou ${rights*20}!";
    }

    fun getResponse(rights: Int): String {
        return when(rights) {
            0 -> "Você pode conseguir..."
            1 -> "Tente mais na próxima"
            2 -> "Melhore."
            3 -> "Na próxima vai!"
            4 -> "Quase la!"
            else -> "Você conseguiu!"
        }
    }

    fun goNextActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }



}