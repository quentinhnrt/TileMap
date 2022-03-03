package fr.iutlens.dubois.carte

import android.content.Context
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
import kotlin.reflect.KClass


class MainActivity : AppCompatActivity() {

    private val map by lazy { TiledArea(R.drawable.decor, Decor(Decor.map)) }
    private val room by lazy { TiledArea(R.drawable.decor, Decor(Decor.room)) }
    private val hero by lazy { BasicSprite(R.drawable.character, map, 20.5F, 11.5F) }
    private val gameView by lazy { findViewById<GameView>(R.id.gameView) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Chargement des feuilles de sprites
        SpriteSheet.register(R.drawable.decor, 5, 4, this)
        SpriteSheet.register(R.drawable.character, 1, 1, this)

        // Par défaut on démarre sur la configuration map
        configMap()

        // On définit les actions des boutons
        findViewById<Button>(R.id.buttonMap).setOnClickListener { configMap() }
        findViewById<Button>(R.id.buttonDrag).setOnClickListener { configDrag() }

        
    }




    private fun configDrag() {
        // Création des différents éléments à afficher dans la vue
        val list = SpriteList() // Notre liste de sprites
        for(i in 1..7){ // On crée plusieurs sprites aléatoires
            list.add(BasicSprite(R.drawable.character, room,
                (room.data.sizeX*Math.random()).toFloat(),
                (room.data.sizeY*Math.random()).toFloat(),
                (0..2).random()))
        }

        // Configuration de gameView
        gameView.apply {
            background = room
            sprite = list
            transform = FitTransform(this, room, Matrix.ScaleToFit.CENTER)
        }
        gameView.onTouch = this::onTouchDrag
        gameView.invalidate() // On demande à rafraîchir la vue

    }


    private fun onTouchDrag(
        point: FloatArray,
        event: MotionEvent,
    ) : Boolean {
        val list = gameView.sprite as? SpriteList ?: return false // On récupère la liste (quitte si erreur)
        return when(event.action) {
            MotionEvent.ACTION_DOWN -> list.setTarget(point[0], point[1]) != null // Sélection du sprite aux coordonnées cliquées
            MotionEvent.ACTION_MOVE -> {
                (list.target as? BasicSprite)?.let {
                    // On déplace le sprite sélectionné aux nouvelles coordonnées
                    it.x = point[0] / it.gridW // Attentions aux unités, ici 1 = une tuile
                    it.y = point[1] / it.gridH
                    gameView.invalidate() // On demande la mise à jour
                    true
                } ?: false
            }
            MotionEvent.ACTION_UP -> {  // On déselectionne
                list.target = null
                true
            }
            else -> false
        }
    }

    private fun configMap() {
        // Configuration de gameView
        gameView.apply {
            background = map
            sprite = hero
            transform = FocusTransform(this, map, hero,12)
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
        if (abs(dx) > abs(dy)) { // calcule la direction du déplacement
            dx = if (dx > 0) 1f else -1f // on se déplace de plus ou moins une case
            dy = 0f
        } else {
            dx = 0f
            dy = if (dy > 0) 1f else -1f
        }
        if (traversable(hero.x+dx, hero.y+dy)) {
            hero.x += dx
            hero.y += dy}
        Log.d("onTouch","${hero.x}, ${hero.y}")
       // Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show()

        testCase()
        gameView.invalidate()
        true
    } else false

    //override fun onResume() {
     //   super.onResume()
     //   val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
    //    val x = sharedPref.getInt("", "")
  //  }

    private fun traversable(x: Float, y: Float): Boolean {
        val case = map.data.get(y.toInt(),x.toInt())
        Log.d("case",case.toString())
       return when(case){
            /*C,8,9*/
            2,3,7,8,11 -> true
            else ->false

        }

    }

    private fun testCase() {
        when (hero.x to hero.y) {
            20.5f to 12.5f -> launch("Crossyroad", CrossyRoadActivity::class)
            4.5f to 1.5f -> launch("fruitcatching", CrossyRoadActivity::class)
        }
    }

    private fun<T : AppCompatActivity> launch(text: String, clazz: KClass<T>) {
        val intent= Intent(this, clazz.java)
        startActivity(intent)
    }
}