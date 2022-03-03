package fr.iutlens.dubois.carte

import android.content.Context
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings.Global.putInt
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import fr.iutlens.dubois.carte.sprite.BasicSprite
import fr.iutlens.dubois.carte.sprite.SpriteList
import fr.iutlens.dubois.carte.sprite.TiledArea
import fr.iutlens.dubois.carte.transform.FitTransform
import fr.iutlens.dubois.carte.transform.FocusTransform
import fr.iutlens.dubois.carte.utils.SpriteSheet
import kotlin.math.abs
import kotlin.math.roundToInt


class DemineurActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demineur)
        SpriteSheet.register(R.drawable.demineur, 10, 10, this)
        SpriteSheet.register(R.drawable.car, 3, 1, this)
        configMap()
    }

    private var touchTimestamp: Long = 0

    //Declare variables
    private val mineMap = MineMap(10,20)
    private val map by lazy { TiledArea(R.drawable.demineur, mineMap) }
    private val gameView by lazy { findViewById<GameView>(R.id.gameView) }
    private var lose = 0
    private fun configMap() {
        gameView.apply {
            background = map
            transform = FitTransform(this, map, Matrix.ScaleToFit.CENTER)
        }
        gameView.onTouch = this::onTouchShow
        gameView.invalidate()
    }
    private fun onTouchShow(
        point: FloatArray,
        event: MotionEvent,
    ) : Boolean {
        return when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchTimestamp= System.currentTimeMillis()
                true
            }
            MotionEvent.ACTION_MOVE -> true
            MotionEvent.ACTION_UP -> {
                if (System.currentTimeMillis()-touchTimestamp > 500) {
                    //Toast.makeText(applicationContext, "clic long",  Toast.LENGTH_SHORT).show()
                    mineMap.flag(point[0]/map.w,point[1]/map.h)
                }
                else {
                    var loseMod = mineMap.show(point[0] / map.w, point[1] / map.h)
                    lose += loseMod
                    if(lose == 3){
                        val session = this.getSharedPreferences("Session", Context.MODE_PRIVATE)

                        with(session.edit()) {
                            putInt("demineurState", 1)
                            putInt("demineurNote", 0)
                           // putInt("score", [score+valeur])
                            apply()
                        }
                        //var state = session.getInt("demineurState", 0)
                        //Log.d("osecour", state.toString())
                        finish()
                    }
                }
                gameView.invalidate()
                true
            }
            else -> false
        }
    }
}