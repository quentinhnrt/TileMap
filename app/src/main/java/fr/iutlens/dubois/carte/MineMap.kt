package fr.iutlens.dubois.carte

import android.util.Log
import android.view.View
import fr.iutlens.dubois.carte.sprite.TileMap

class MineMap( override val sizeX: Int, override val sizeY: Int) : TileMap {


    var demineurGrid = Array(sizeY) { IntArray(sizeX) }
    var displayGrid = Array(sizeY) { IntArray(sizeX) }
    var mineCount = 10

    fun generateMines() {
        //Generate grid
        for (i in 0..mineCount) {
            demineurGrid[(0..sizeY-1).random()][(0..sizeX-1).random()] = 1 //
        }
    }

    init {
        generateMines()
    }

    fun generateMines(view: View) {
        var temp_string = ""
        for (i in 0..sizeY-1) {
            for (j in 0..sizeX-1){
                temp_string += demineurGrid[i][j].toString()
            }
        }
        Log.d("itshereman", temp_string.toString())
    }


    override fun get(x: Int, y: Int): Int {
        return demineurGrid[y][x]
    }

    fun show(x: Float, y: Float) {
        Log.d("show","$x : $y")
        demineurGrid[y.toInt()][x.toInt()] =2;
    }


}
