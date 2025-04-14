package br.ufpr.mobile.trabalho1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.ufpr.mobile.trabalho1.activity.CreatorsActivity
import br.ufpr.mobile.trabalho1.activity.GameActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun showCreators(view: View){
        val intent = Intent(this, CreatorsActivity::class.java)
        startActivity(intent)
    }

    fun startGame(view: View) {
        val intent = Intent(this, GameActivity::class.java)
        val nameImput = findViewById<EditText>(R.id.editTextNome)

        val name = nameImput.text.toString()

        var user = User(name,0)

        intent.putExtra("userName", name)

        startActivity(intent)
    }
}