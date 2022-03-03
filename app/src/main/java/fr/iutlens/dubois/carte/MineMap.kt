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
    fun showDirection (x: Int, y: Int, direction: String) {
        var counter = 1
        var differenceX = x
        var differenceY = y
        var limit = 0
        if (direction == "right"){
            var limit = sizeX - x
        }
        else if (direction == "down"){
            var limit = sizeY - y
        }
        while (counter > -1 && counter < limit) {
            when (direction) {
                "up" -> differenceY = 0-counter
                "right" -> differenceX = counter
                "down" -> differenceY = counter
                "left" -> differenceX = 0-counter
            }
            if (demineurGrid[y+differenceY][x+differenceX] != 0)
            {
                counter = -1
            }
            else {
                displayGrid[y+differenceY][x+differenceX] = 1
                counter++
            }
        }
    }

    fun showSpace(x: Int, y: Int){


        if (demineurGrid[y][x] != 0) return //garantis pas de mine voisine
        if (displayGrid[y][x] !=0 ) return //case pas encore visible

        displayGrid[y][x] = 1

        for (i in x-1..x+1)
            for (j in y-1..y+1){
                if (validTile(i,j))
                    showSpace(i,j)
            }



        for (i in x-1..x+1)
            for (j in y-1..y+1){
                if (validTile(i,j)&&displayGrid[j][i] ==0 && demineurGrid[j][i] != 0)
                    displayGrid[j][i] = demineurGrid[j][i]
            }


        // Si case vide
        // alors visiter tous les voisins vide (composante connexe)

        // montrer la case et ses voisins directs (si nécessaires)
    }

    fun showSpaceOld(x: Int, y: Int) {
        var check_up = 1
        var check_down = 1
        var check_left = 1
        var check_right = 1
        while (check_up > -1 && y-check_up >= 0) {
            if (demineurGrid[y-check_up][x] != 0) {
                check_up = -1
            }
            else {
                displayGrid[y.toInt() - check_up][x.toInt()] = 1
                check_up++
            }
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
        while (check_left > -1 && x-check_left >= 0) {
            if (demineurGrid[y][x-check_left] != 0) {
                check_left = -1
            }
            else {
                displayGrid[y.toInt()][x.toInt()-check_left] = 1
                check_left++
            }
        }
        while (check_right > -1 && x+check_right < sizeX) {
            if (demineurGrid[y][x+check_right] != 0) {
                check_right = -1
            }
            else {
                displayGrid[y.toInt()][x.toInt()+check_right] = 1
                check_right++
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
                if (tile_value != 0) {
                    demineurGrid[tile_y][tile_x] = tile_value
                    //displayGrid[tile_y][tile_x] = tile_value
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
    }
    init {
        generateMines()
    }
    override fun get(x: Int, y: Int): Int {
        return displayGrid[y][x]
    }
    fun show(x: Float, y: Float): Int {
        var tile_value = demineurGrid[y.toInt()][x.toInt()]
        var loseMod = 0
        if (tile_value == 0) {
            tile_value = 1
            showSpace(x.toInt(),y.toInt())
        }
        if (tile_value == 2 || tile_value == 3 || tile_value == 4){
            loseMod = 1
        }
        displayGrid[y.toInt()][x.toInt()] = tile_value
        return loseMod
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
