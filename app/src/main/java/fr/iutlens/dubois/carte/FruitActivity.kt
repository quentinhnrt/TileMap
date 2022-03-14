package fr.iutlens.dubois.carte

import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import fr.iutlens.dubois.carte.sprite.BasicSprite
import fr.iutlens.dubois.carte.sprite.SpriteList
import fr.iutlens.dubois.carte.sprite.TiledArea
import fr.iutlens.dubois.carte.transform.FitTransform
import fr.iutlens.dubois.carte.utils.RefreshHandler
import fr.iutlens.dubois.carte.utils.SpriteSheet
import fr.iutlens.dubois.carte.utils.TimerAction

class FruitActivity : AppCompatActivity(), TimerAction {

    private lateinit var listBonus: SpriteList
    private lateinit var timer: RefreshHandler
    private val room by lazy { TiledArea(R.drawable.decor, Decor(Decor.room)) }
    private val hero by lazy { BasicSprite(R.drawable.charactero, room, 5.5F, 8.5F) }
    private val fruit by lazy { BasicSprite(R.drawable.fruit, room, 4.5F, 8.5F) }
    private var cafe = 0
    private val gameView by lazy { findViewById<GameView>(R.id.gameViewfruit) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruitcatching)

        // Chargement des feuilles de sprites
        SpriteSheet.register(R.drawable.decor, 5, 4, this)
        SpriteSheet.register(R.drawable.character_fruit, 1, 1, this)
        SpriteSheet.register(R.drawable.fruit, 1, 1, this)
        SpriteSheet.register(R.drawable.cafe_2, 1, 1, this)



        // Par défaut on démarre sur la configuration map
        configMap()

        // On définit les actions des boutons
        timer = RefreshHandler(this)
        timer.scheduleRefresh(500)

    }




    private fun configMap() {
         listBonus = SpriteList() // Notre liste de sprites
        for(i in 1..3){ // On crée plusieurs sprites aléatoires
            listBonus.add(BasicSprite(R.drawable.cafe_2, room,
                (room.data.sizeX*Math.random()).toFloat(),
                (room.data.sizeY*Math.random()*0.2f).toFloat(),
                0))
        }
        val listAll = SpriteList()
        listAll.add(hero)
        listAll.add(listBonus)

        // Configuration de gameView
        gameView.apply {
            background = room
            sprite = listAll
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

    override fun update() {
        timer.scheduleRefresh(100)
        listBonus.list.forEach {
            (it as BasicSprite).y +=0.15f;
            if(it.boundingBox.intersect(hero.boundingBox)){
                ++cafe
                if (cafe>=15)finish()
            }


        }
        listBonus.list.retainAll{
            (it as BasicSprite).y < room.data.sizeY  && !it.boundingBox.intersect(hero.boundingBox)

        }
        if (Math.random()<0.1f) listBonus.add(BasicSprite(R.drawable.cafe_2, room,
            (room.data.sizeX*Math.random()).toFloat(),
            (room.data.sizeY*Math.random()*0.2f).toFloat(),
            0))
        gameView.invalidate()
    }


    }







