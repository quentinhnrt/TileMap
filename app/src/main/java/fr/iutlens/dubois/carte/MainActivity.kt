package fr.iutlens.dubois.carte

import android.content.Context
import android.content.Intent
import android.graphics.Matrix
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import fr.iutlens.dubois.carte.sprite.AnimatedSprite
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
    private val hero by lazy { AnimatedSprite(R.drawable.charactero, map, 8.5F, 3.5F) }
    private val gameView by lazy { findViewById<GameView>(R.id.gameView) }
    private val vieText by lazy { findViewById<TextView>(R.id.vie) }
    private val scoreText by lazy { findViewById<TextView>(R.id.score) }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Chargement des feuilles de sprites
        SpriteSheet.register(R.drawable.decor, 5, 4, this)
        SpriteSheet.register(R.drawable.charactero, 16, 1, this)

        // Par défaut on démarre sur la configuration map
        configMap()

        // On définit les actions des boutons
       // findViewById<Button>(R.id.buttonMap).setOnClickListener { reset() }
     //   findViewById<Button>(R.id.buttonDrag).setOnClickListener { configDrag() }


        val session = getPreferences(Context.MODE_PRIVATE) ?: return
        val score = session.getInt("score", 0)
        val vie = session.getInt("vie", 5)

        vieText.text = vie.toString()
        scoreText.text = score.toString()


    }

    private fun reset() {
        val session = getSharedPreferences("Session", Context.MODE_PRIVATE) ?: return
        val score = session.getInt("score", 0)
        val vie = session.getInt("vie", 5)
        with(session.edit()){
            putInt("vie", 5)
            putInt("score", 0)
            apply()
        }
        vieText.text = vie.toString()
        scoreText.text = score.toString()
    }

    override fun onRestart() {
        super.onRestart()

        val session = getSharedPreferences("Session",Context.MODE_PRIVATE) ?: return
        val score = session.getInt("score", 0)
        val vie = session.getInt("vie", 5)

        vieText.text = vie.toString()
        scoreText.text = score.toString()
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
            hero.move(dx,dy);
            //hero.x += dx
            //hero.y += dy
        }
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
        if ((x+1).toInt()-1 !in 0 until map.data.sizeX) return false
        if ((y+1).toInt()-1 !in 0 until map.data.sizeY) return false
        val case = map.data.get(x.toInt(),y.toInt())
        Log.d("case",case.toString())
       return when(case){
            /*C,8,9*/
            2,3,7,8,11 -> true
            else ->false

        }

    }

    private fun testCase() {
        when (hero.x to hero.y) {
            20.5f to 12.5f -> door("crossyState")
            14.5f to 3.5f -> door("fruitState")
            17.5f to 1.5f -> door("demineurState")
            11.5f to 1.5f -> door("quizState")

        }
    }

    private fun door(name: String){
       val session = getSharedPreferences("Session", Context.MODE_PRIVATE) ?: return
        var gameState = session.getInt(name, 0)
        if(gameState == 0){
            when(name){
                "crossyState" -> launch(name, CrossyRoadActivity::class)
                "demineurState" -> launch(name, DemineurActivity::class)
                "fruitState" -> launch(name, FruitActivity::class)
                "quizState" -> launch(name, Quiz::class)

            }

        }
        else{
            Toast.makeText(this,"Jeu déja fini ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun<T : AppCompatActivity> launch(text: String, clazz: KClass<T>) {
        val intent= Intent(this, clazz.java)
        startActivity(intent)
    }
}