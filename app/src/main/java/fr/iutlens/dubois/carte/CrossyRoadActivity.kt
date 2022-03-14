package fr.iutlens.dubois.carte


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity

import fr.iutlens.dubois.carte.sprite.BasicSprite
import fr.iutlens.dubois.carte.sprite.TiledArea
import fr.iutlens.dubois.carte.transform.FocusTransform
import fr.iutlens.dubois.carte.utils.RefreshHandler
import fr.iutlens.dubois.carte.utils.TimerAction
import kotlin.math.abs

class CrossyRoadActivity : AppCompatActivity(), TimerAction {
    private val theRoad by lazy { TiledArea(R.drawable.road, Decor(Decor.crossy)) }
    private val hero by lazy { BasicSprite(R.drawable.character64, theRoad, 6.5F, 1.5F) }
    private val gameView by lazy { findViewById<GameView>(R.id.gameViewRoad) }
    private val tab = Decor.crossy
    private val beginX = hero.x
    private val beginY = hero.y
    private var timer: RefreshHandler? = null;
    private var lose = 0;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crossy_road)
        // Chargement des feuilles de sprites

        timer = RefreshHandler(this)
        timer?.scheduleRefresh(500)

        gameView.apply {
            background = theRoad
            sprite = hero
            transform = FocusTransform(this, theRoad, hero, 12)
        }
        gameView.onTouch = this::onTouchMap
        gameView.invalidate()

        //        Timer("toY").schedule(0, 500) {


        val session = getSharedPreferences("Session", Context.MODE_PRIVATE) ?: return
        var vie = session.getInt("vie", 5)
        with(session.edit()){
            putInt("vie", vie-1)
            apply()
        }
    }

    private fun win() {
        timer = null
        val note = when (lose) {
            0 -> 20
            1 -> 15
            2 -> 10
            else -> {
                0
            }
        }

        val session = this.getSharedPreferences("Session", Context.MODE_PRIVATE) ?: return
        var score = session.getInt("score", 0)
        with(session.edit()) {
            putInt("crossyState", 1)
            putInt("crossyNote", note)
            putInt("score", score + note)
            apply()
        }

        finish()
//        val intent= Intent(this, MainActivity::class.java)
//        startActivity(intent)
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

        check(hero.x, hero.y)



        true
    } else false

    private fun gameOver() {
        if (lose < 3) {
            lose += 1
            hero.x = beginX
            hero.y = beginY
        } else {
            val session = getSharedPreferences("Session", Context.MODE_PRIVATE) ?: return
            var vie = session.getInt("vie", 5)
            with(session.edit()) {
                putInt("crossyState", 1)
                putInt("crossyNote", 0)
                putInt("vie", vie - 1)
                apply()
            }
            finish()
        }

    }

    override fun update() {
        if (timer == null) return

        timer?.scheduleRefresh(300)
        val y = hero.y
        val x = hero.x

        check(x, y)

        hero.y += 1f
        gameView.invalidate()


    }

    private fun check(x: Float, y: Float) {
        when (tab[y.toInt()][x.toInt()]) {
            '6', '4', '5', '3' -> gameOver()
            '7' -> win()
        }
    }
}