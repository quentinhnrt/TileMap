package fr.iutlens.dubois.carte

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Quiz : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
    }

    fun next(view: View) {
        val question: TextView = findViewById(R.id.textViewQuestion);
        question.text = "On peut coder un site en :";
    }


}