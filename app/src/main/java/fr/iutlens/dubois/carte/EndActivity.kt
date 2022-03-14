package fr.iutlens.dubois.carte

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.content.SharedPreferences




class EndActivity : AppCompatActivity() {
    private val textView by lazy {findViewById<TextView>(R.id.endMessage)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end)
        var session = getSharedPreferences("Session", Context.MODE_PRIVATE) ?: return
        var result = session.getInt("result", 0)
        var moy = session.getInt("moy", 10)
        if(result == 0){
            textView.text = "Vous avez votre année avec une moyenne de "+moy
        }else{
            textView.text = "Vous n'avez pas votre année, votre moyenne est de "+moy
        }

        findViewById<Button>(R.id.restart).setOnClickListener { restartGame() }

    }

    private fun restartGame() {
        val settings: SharedPreferences =
            getSharedPreferences("Session", Context.MODE_PRIVATE)
        settings.edit().clear().commit()

        val intent = Intent(applicationContext, LauncherActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)


    }


}