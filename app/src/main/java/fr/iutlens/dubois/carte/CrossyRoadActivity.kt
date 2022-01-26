package fr.iutlens.dubois.carte


import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

import fr.iutlens.dubois.carte.sprite.BasicSprite
import fr.iutlens.dubois.carte.sprite.TiledArea
import fr.iutlens.dubois.carte.transform.FocusTransform
import java.util.*
import kotlin.concurrent.schedule
import kotlin.math.abs

class CrossyRoadActivity : AppCompatActivity() {
    private val theRoad by lazy { TiledArea(R.drawable.road, Decor(Decor.crossy)) }
    private val hero by lazy { BasicSprite(R.drawable.character64, theRoad, 6.5F, 1.5F) }
    private val gameView by lazy { findViewById<GameView>(R.id.gameViewRoad) }
    private val tab = Decor.crossy
    private val beginX = hero.x
    private val beginY = hero.y


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crossy_road)
        // Chargement des feuilles de sprites



        gameView.apply {
            background = theRoad
            sprite = hero
            transform = FocusTransform(this, theRoad, hero,12)
        }
        gameView.onTouch = this::onTouchMap
        gameView.invalidate()

        Timer("toY").schedule(0, 500) {

            val y = hero.y
            val x = hero.x

            when(tab[y.toInt()][x.toInt()]){
                '6','4','5','3' -> gameOver()
                '7' -> cancel()
            }

            hero.y += 1f
            gameView.invalidate()

            when(tab[y.toInt()][x.toInt()]){
                '6','4','5','3' -> gameOver()
                '7' -> win()
            }


        }
    }

    private fun win() {
        val intent= Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun onTouchMap(
        point: FloatArray,
        event: MotionEvent
    ) = if (event.action == MotionEvent.ACTION_DOWN) {
        var dx = point[0] - hero.xCenter() // calcule le vecteur entre le sprite et la zone touchée
        var dy = point[1] - hero.yCenter()
        //       Log.d("move", "$dx/$dy")
        if (abs(dx) > abs(dy)) { // calcule la direction du déplacement
            dx = if (dx > 0) 1f else -1f // on se déplace de plus ou moins une case
            dy = 0f
        } else {
            dx = 0f
            dy = 0f
        }
        hero.x += dx
        hero.y += dy
        gameView.invalidate()

        val y = hero.y
        val x = hero.x

        when(tab[y.toInt()][x.toInt()]){
            '6','4','5','3' -> gameOver()
        }







        true
    } else false

    private fun gameOver(){
        hero.x = beginX
        hero.y = beginY
    }
}