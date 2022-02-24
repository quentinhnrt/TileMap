package fr.iutlens.dubois.carte

import android.util.Log
import android.view.View
import fr.iutlens.dubois.carte.sprite.TileMap

class MineMap( override val sizeX: Int, override val sizeY: Int) : TileMap {
    var demineurGrid = Array(sizeY) { IntArray(sizeX) }
    var displayGrid = Array(sizeY) { IntArray(sizeX) }
    var reds = 3
    var greens = reds
    var blues = reds
    fun generateMines() {
        while ((reds + greens + blues > 0)) {
            val tile_x = (0..sizeX - 1).random()
            val tile_y = (0..sizeY - 1).random()
            var tile_value = demineurGrid[tile_y][tile_x]
            if (tile_value == 0) {
                if (reds > 0) {
                    tile_value = 2
                    reds--
                }
                else if (greens > 0) {
                    tile_value = 3
                    greens--
                }
                else {
                    tile_value = 4
                    blues--
                }
            }
            if (tile_value != 0) {
                for (i in 0..2) {
                    for (j in 0..2) {
                        demineurGrid[tile_y+i-1][tile_x+j-1] = tile_value + 3
                    }
                }
                demineurGrid[tile_y][tile_x] = tile_value
            }
        }
    }
    init {
        generateMines()
    }
    override fun get(x: Int, y: Int): Int {
        return displayGrid[y][x]
    }
    fun show(x: Float, y: Float) {
        var tile_value = demineurGrid[y.toInt()][x.toInt()]
        if (tile_value == 0) {
            tile_value = 1
            //for (i in 0..2) {
                //for (j in 0..2) {
                    //if ((i + j)%2 != 0 && demineurGrid[y.toInt()+i][x.toInt()+j] == 0) {
                        //show(x+j,y+i)
                    //}
                //}
            //}
        }
        displayGrid[y.toInt()][x.toInt()] = tile_value
    }
}
