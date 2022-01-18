package fr.iutlens.dubois.carte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.iutlens.dubois.carte.utils.SpriteSheet

class LauncherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        SpriteSheet.register(R.drawable.road, 5, 4, this)
        SpriteSheet.register(R.drawable.car, 3, 1, this)
    }

    fun main(view: android.view.View) {
        val intent=Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun Fruit(view: android.view.View) {
        val intent=Intent(this, fruitcatching::class.java)
        startActivity(intent)}

    fun Crossy(view: android.view.View) {
        val intent=Intent(this, CrossyRoadActivity::class.java)
        startActivity(intent)
    }

}