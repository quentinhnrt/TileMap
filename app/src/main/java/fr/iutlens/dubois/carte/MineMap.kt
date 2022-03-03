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
    fun validTile(x: Int,y: Int): Boolean {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY
    }
    fun showSpace(x: Int, y: Int) {
        var check_up = 1
        var check_down = 1
        while (check_up > -1 && y-check_up >= 0) {
            if (demineurGrid[y-check_up][x] != 0) {
                check_up = -1
            }
            else {
                displayGrid[y.toInt()-check_up][x.toInt()] = 1
                check_up++
            }
            var check_left = 0
        }
        while (check_down > -1 && y+check_down < sizeY) {
            if (demineurGrid[y+check_down][x] != 0) {
                check_down = -1
            }
            else {
                displayGrid[y.toInt()+check_down][x.toInt()] = 1
                check_down++
            }
        }
    }
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
                demineurGrid[tile_y][tile_x] = tile_value
                displayGrid[tile_y][tile_x] = tile_value
                val neighbours = arrayOf(-1,0,1)
                for (i in neighbours) {
                    for (j in neighbours) {
                        if (validTile(tile_x+j, tile_y+i)) {
                            //displayGrid[tile_y+i][tile_x+j] = tile_value + 3
                            if (i != j) {
                                demineurGrid[tile_y + i][tile_x + j] = tile_value + 3
                            }
                            else if (i !=0) {
                                demineurGrid[tile_y + i][tile_x + j] = tile_value + 3
                            }
                        }
                    }
                }
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
            showSpace(x.toInt(),y.toInt())
        }
        displayGrid[y.toInt()][x.toInt()] = tile_value
    }
    fun flag(x: Float, y: Float) {
        var display_value = displayGrid[y.toInt()][x.toInt()]
        when (display_value) {
            0 -> display_value = 17
            17, 18 -> display_value++
            19 -> display_value = 0
        }
        displayGrid[y.toInt()][x.toInt()] = display_value
    }
}
