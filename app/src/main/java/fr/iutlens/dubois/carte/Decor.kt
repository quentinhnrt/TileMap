package fr.iutlens.dubois.carte

import fr.iutlens.dubois.carte.sprite.TileMap

/**
 * Created by dubois on 27/12/2017.
 */
class Decor(dataSrc: Array<String>? = null) : TileMap {

    private val DIGITS = "123456789ABCDEFGHIJKL0"

    private val data: List<List<Int>> =  (dataSrc ?: map).map { line -> line.map { c -> DIGITS.indexOf(c) } }

    override operator fun get(i: Int, j: Int): Int { return data[i][j] }

    override val sizeY = data[0].size
    override val sizeX = data.size


    companion object {
        val mine = arrayOf(
            "10020",
            "00000",
            "01000",
            "00000",
            "00031",

        )

         val room = arrayOf(
            "1222232222225",
            "677778777777A",
            "BCCCCCCCCCCCG",
            "BCCCCCCCCCCCG",
            "BCCCCCCCCCCCG",
            "BCCCCCCCCCCCG",
            "BCCCCCCCCCCCG",
            "BCCCCCCCCCCCG",
            "BCCCCCCCCCCCG",
            "122DE222DE225",
            "677IJ777IJ77A",

            )

        val crossy = arrayOf(
            "3331112111333",
            "3331112111333",
            "3331412111333",
            "3331112114333",
            "3331152111333",
            "3331116151333",
            "3331112114333",
            "3331112111333",
            "3335112115333",
            "3331112161333",
            "3331412111333",
            "3331112111333",
            "3331412151333",
            "3331116111333",
            "3331112114333",
            "3336112115333",
            "3331112111333",
            "3331112611333",
            "3331112111333",
            "3331412111333",
            "3331112111333",
            "3331152111333",
            "3331112111333",
            "3331416111333",
            "3331112111333",
            "3331112141333",
            "3331142111333",
            "3335112111333",
            "3331112111333",
            "3331152161333",
            "3331112111333",
            "3331412141333",
            "3331112115333",
            "3331112611333",
            "3337777777333",
            "3331112111333",


        )

          val map = arrayOf(
            "22223222222322222322342242222222422",
            "77778777777877F77877897797777777977",
            "CCCCCCCCCCCCCCKCCCCCCCCCCCCCCCCCCCC",
              "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",
            "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",
            "1222325222232522232BCCG242222122422",
            "677787A777787A77787BCCG797777677977",
            "BCCCCCGCCCCCCGCCCCCBCCGCCCCCCBCCCCC",
              "BCCCCCGCCCCCCGCCCCCBCCGCCCCCCBCCCCC",
            "BCCCCCGCCCCCCGCCCCCBCCGCCCCCCBCCCCC",
            "122DE2222DE2222DE22BCCG22DE2222DE22",
            "677IJ7777IJ7777IJ77BCCG77IJ7777IJ77",
            "HHHHHHHHHHHHHHHHHHH1345HHHHHHHHHHHH",
            "HHHHHHHHHHHHHHHHHHH689AHHHHHHHHHHHH"
        )

    }
}