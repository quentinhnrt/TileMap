package fr.iutlens.dubois.carte

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Quiz : AppCompatActivity() {

    val q1 = arrayOf(
        arrayOf(
            "Avec GET où se trouve les informations à envoyer au Serveur ?",
            "1/ Dans l'entête", "2/ Dans l'url", "3/ Dans un cookie du site","4/ Sur le fichier READ ME"),
        arrayOf(
            "On peut coder un site en :", "1/Latin", "2/MySQL", "3/PHP", "4/AI"),
        arrayOf(
            "Pour créer des documents intéractifs ou d'impression on utilise :",
            "1/ InDesign", "2/ Photoshop", "3/ Illustrator","Audition"),
        arrayOf(
            "Quel est le numéro de salle de l'Amphi ?", "1/ 00 F", "2/Il n'y en a pas", "3/ 05 F", "4/ 666 F"),
        arrayOf(
            "Est-ce que MMI c'est génial ?",
            "1/ Oui", "2/ Non","Bof" ,"Drip")
    )

    val r1 = arrayOf(2, 3, 1, 2, 1)

    val id = arrayOf(R.id.textViewQuestion, R.id.RadioButton1,R.id.RadioButton2,R.id.RadioButton3,R.id.RadioButton4)

    private var nQuestion = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        loadQ(nQuestion)
    }



    private var note: Int = 0

    fun next(view: View) {
        //val question: TextView = findViewById(R.id.textViewQuestion)
        val RadioButton = findViewById<RadioButton>(id[r1[nQuestion]])
        if (RadioButton.isChecked) {
            note++
        }
        Log.d("score", note.toString())

        nQuestion++
        loadQ(nQuestion)

    }

    private fun loadQ(n: Int) {
        val textView = id.map { findViewById<TextView>(it) }

        var i: Int

        for (i in id.indices) {
            textView[i].text = q1[n][i]
        }
    }


}