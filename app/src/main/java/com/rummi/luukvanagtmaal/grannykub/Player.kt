package com.rummi.luukvanagtmaal.grannykub

import android.widget.TableLayout
import kotlinx.android.synthetic.main.activity_play.view.*


class Player(val name: String = "Henk",
             private var table: TableLayout,
             var count: Int = 0,
             var plankTiles: MutableList<Tile> = mutableListOf()) {


    fun drawTiles(n: Int, pack: MutableList<Tile>) {
        for (i in IntRange(0, n-1)) {
            plankTiles.add(pack[i])
            addTileToTable(pack[i])
            pack.removeAt(i) // delete tile from pack
            count += 1
        }
    }


    private fun addTileToTable(tile: Tile) {
        if (count <= 15) {
            table.row1.addView(tile)
        }
        if (count in 16..30) {
            table.row2.addView(tile)
        }
    }

    fun sortPlank() {
        val sortedList:MutableList<Tile> = plankTiles.sortedWith(compareBy {it.getProp("colour") + it.getProp("num")}).toMutableList()
        table.row1.removeAllViews()
        table.row2.removeAllViews()
        for (tile: Tile in sortedList) {
            addTileToTable(tile)
        }


    }
}