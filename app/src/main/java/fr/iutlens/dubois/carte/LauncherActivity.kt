package fr.iutlens.dubois.carte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)
    }

    fun main(view: android.view.View) {
        val intent=Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun Fruit(view: android.view.View) {
        val intent=Intent(this, fruitcatching::class.java)
        startActivity(intent)}

    fun Crossy(view: android.view.View) {
        val intent=Intent(this, Crossyroad::class.java)
        startActivity(intent)
    }
    fun Demineur(view: android.view.View) {
        val intent=Intent(this, DemineurActivity::class.java)
        startActivity(intent)
    }
    fun Quiz(view: android.view.View) {
        val intent=Intent(this, Crossyroad::class.java)
        startActivity(intent)
    }

}