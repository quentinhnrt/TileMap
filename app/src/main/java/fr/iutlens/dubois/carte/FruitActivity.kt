package fr.iutlens.dubois.carte

import android.content.Intent
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.Toast
import fr.iutlens.dubois.carte.sprite.BasicSprite
import fr.iutlens.dubois.carte.sprite.SpriteList
import fr.iutlens.dubois.carte.sprite.TiledArea
import fr.iutlens.dubois.carte.transform.FitTransform
import fr.iutlens.dubois.carte.transform.FocusTransform
import fr.iutlens.dubois.carte.utils.SpriteSheet
import kotlin.math.abs

class FruitActivity : AppCompatActivity() {

    private val room by lazy { TiledArea(R.drawable.decor, Decor(Decor.room)) }
    private val hero by lazy { BasicSprite(R.drawable.character, room, 5.5F, 8.5F) }
    private val fruit by lazy { BasicSprite(R.drawable.fruit, room, 4.5F, 8.5F) }

    private val gameView by lazy { findViewById<GameView>(R.id.gameViewfruit) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruitcatching)

        // Chargement des feuilles de sprites
        SpriteSheet.register(R.drawable.decor, 5, 4, this)
        SpriteSheet.register(R.drawable.character, 1, 1, this)
        SpriteSheet.register(R.drawable.fruit, 1, 1, this)

        // Par défaut on démarre sur la configuration map
        configMap()

        // On définit les actions des boutons

    }




    private fun configMap() {
        // Configuration de gameView
        gameView.apply {
            background = room
            sprite = hero
            transform = FitTransform(this, room, Matrix.ScaleToFit.CENTER)
        }
        gameView.onTouch = this::onTouchMap
        gameView.invalidate()
    }

    private fun onTouchMap(
        point: FloatArray,
        event: MotionEvent
    ) = if (event.action == MotionEvent.ACTION_DOWN) {
        var dx = point[0] - hero.xCenter() // calcule le vecteur entre le sprite et la zone touchée
        var dy = point[1] - hero.yCenter()
        //       Log.d("move", "$dx/$dy")
        //if (abs(dx) > abs(dy)) { // calcule la direction du déplacement
            dx = if (dx > 0) 1f else -1f // on se déplace de plus ou moins une case
            dy = 0f
            hero.x += dx
            hero.y += dy
       // }

        Log.d("onTouch","${hero.x}, ${hero.y}")
        // Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show()

        testCase()
        gameView.invalidate()
        true
    } else false

    private fun testCase() {
        when (hero.x to hero.y) {
            11.5f to 1.5f -> launch("Croasy Road")
            4.5f to 1.5f -> launch(" Crous Catching")
        }
    }
    private fun launch(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

    }

}

