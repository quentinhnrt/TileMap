package fr.iutlens.dubois.carte

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

class Demineur : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demineur)
        generateMines()
    }
    //Declare variables
    var gridWidth = 10
    var gridHeight = 2*gridWidth
    var demineurGrid = Array(gridHeight) { IntArray(gridWidth) }
    var mineCount = 21
    fun generateMines() {
        //Generate grid
        //for (i in mineCount) {

        //}
        Log.d("itshereman", demineurGrid[0].toString())
    }
}