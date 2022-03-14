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
        SpriteSheet.register(R.drawable.character64, 1, 1, this)
        SpriteSheet.register(R.drawable.fruit, 1, 1, this)
        SpriteSheet.register(R.drawable.charactero, 16, 1, this)
    }

    fun main(view: android.view.View) {
        val intent=Intent(this, CrossyRoadActivity::class.java)
        startActivity(intent)
    }

    fun Fruit(view: android.view.View) {
        val intent=Intent(this, FruitActivity::class.java)
        startActivity(intent)}

    fun Crossy(view: android.view.View) {
        val intent=Intent(this, CrossyRoadActivity::class.java)
        startActivity(intent)
    }
    fun Demineur(view: android.view.View) {
        val intent=Intent(this, DemineurActivity::class.java)
        startActivity(intent)
    }
    fun Quiz(view: android.view.View) {
        val intent=Intent(this, Quiz::class.java)
        startActivity(intent)
    }

}