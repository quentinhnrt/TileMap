package fr.iutlens.dubois.carte

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Quiz : AppCompatActivity() {


    val q = arrayOf(
        arrayOf(
            "Avec GET où se trouve les informations à envoyer au Serveur ?",
            "1/ Dans l'entête", "2/ Dans l'url", "3/ Dans un cookie du site"
        ),
        arrayOf("On peut coder un site en :", "1/HTML", "2/Python", "3/PHP", "4/JavaScript"),
        arrayOf(
            "Avec GET où se trouve les informations à envoyer au Serveur ?",
            "1/ Dans l'entête", "2/ Dans l'url", "3/ Dans un cookie du site"
        ),
        arrayOf("On peut coder un site en :", "1/HTML", "2/Python", "3/PHP", "4/JavaScript"),
        arrayOf(
            "Avec GET où se trouve les informations à envoyer au Serveur ?",
            "1/ Dans l'entête", "2/ Dans l'url", "3/ Dans un cookie du site"
        )
    )

    val id = arrayOf(R.id.textViewQuestion, R.id.radioButtonR1,R.id.radioButtonR2,R.id.radioButtonR3)

    private var nQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        loadQ(nQuestion)
    }

    fun next(view: View) {
        //val question: TextView = findViewById(R.id.textViewQuestion)
        nQuestion++
        loadQ(nQuestion)

    }

    private fun loadQ(n: Int) {
        val textView = id.map { findViewById<TextView>(it) }

        var i: Int

        for (i in id.indices) {
            textView[i].text = q[n][i]

        }
    }


}