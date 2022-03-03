package fr.iutlens.dubois.carte.sprite

import fr.iutlens.dubois.carte.sprite.TiledArea
import kotlin.math.abs

class AnimatedSprite(id: Int, map: TiledArea, x: Float, y: Float) : BasicSprite(id,map,x,y){
    fun move(dx: Float, dy: Float) {
        x += dx
        y += dy

        step = (step+1)%4

        if (abs(dx) > abs(dy)) { // calcule la direction du déplacement
            dir = if (dx > 0) 3 else 1 // on se déplace de plus ou moins une case

        } else {
            dir = if (dy > 0) 0 else 2
        }

        ndx = 4*dir + step
    }

    var dir: Int=0;
    var step: Int=0;


}
