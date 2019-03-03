package com.rummi.luukvanagtmaal.grannykub

import android.content.Context
import android.graphics.Color.rgb
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.DragEvent
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_play.view.*
import java.sql.RowId
import kotlin.math.ceil

val BLUE: Int = rgb(0, 0, 255)
val RED: Int = rgb(255, 0, 0)
val YELLOW: Int = rgb(255,235,0)
val BLACK: Int = rgb(0, 0, 0)

//class Tile(val colour: Int = 0, val num: Int = 0) {
//    val name = "$colour $num"
//
//    private fun checkPlay(): Boolean {
//        return true
//    }
//}
class Tile: TextView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        textSize = 24.toFloat()
        textAlignment = View.TEXT_ALIGNMENT_GRAVITY
        gravity = Gravity.CENTER
    }

    fun setup(plank: TableLayout, colour: Int, num: Int) {
        setTextColor(colour)
        text = num.toString()
        width = plank.layoutParams.width / 15
        height = plank.layoutParams.height / 2

        when (colour) {  // check colours and then set the border around the textview to that colour
            BLACK -> setBackgroundResource(R.drawable.black_edge)
            RED -> setBackgroundResource(R.drawable.red_edge)
            YELLOW -> setBackgroundResource(R.drawable.yellow_edge)
            BLUE -> setBackgroundResource(R.drawable.blue_edge)
        }
    }
    fun findSwapTarget(tileToSwap: Tile, event: DragEvent): Boolean {
        return if (event.x >= x && event.x <= x + width && event.y >= y && event.y <= y + height) {
            Log.d(context.toString(), "tile $this is the swap target of $tileToSwap")
            true
        } else {
            false
        }
    }

    fun swapPosition(other: Tile, owner: ViewGroup, targetRow: ViewGroup) {
        val pos: List<Float> = listOf(x, y)
        if (owner.id != targetRow.id) {
            Log.d(context.toString(), "Attempt to switch tile with a tile in the other row")
            owner.removeView(this) // remove this tile from its parent
            targetRow.addView(this) // add this tile to the other row
            targetRow.removeView(other) // remove the other tile from its parent
            owner.addView(other)  // add that tile to this one
        }
        x = other.x
        y = other.y
        other.x = pos[0]
        other.y = pos[1]
    }

    fun getProp(item: String): Int{
        return if (item == "colour") {
            currentTextColor
        } else {
            text.toString().toInt()
        }
    }

    fun within(vg: ViewGroup, drag: DragEvent): Boolean {
        Log.d(this.toString(), "checking if tile ${this.text} is within ViewGroup $vg...")
//        val viewPos = IntArray(2)
//      getLocationOnScreen(viewPos) // absolute position of the tile on screen
//        val X: Int = viewPos[0]
//        val Y: Int = viewPos[1]
        val X: Float = drag.x + vg.left   // drag.x is relative to parent -> add left x of parent
        val Y: Float = drag.y + vg.top   // same for y -> add top
        // TODO: Check if tile is within plank: else, it has to be inside the board!
        return if (X >= vg.left && X <= vg.right && Y >= vg.top && Y <= vg.bottom) { // always true...
            Log.d(context.toString(), "X = $X, Y= $Y, [left,right,top,bot] = ${listOf<Int>(vg.left, vg.right, vg.top, vg.bottom)}")
            Log.d(context.toString(), "Going to return true")
            true
        } else {
            Log.d(context.toString(), "X = $X, Y= $Y, [left,right,top,bot] = ${listOf<Int>(vg.left, vg.right, vg.top, vg.bottom)}")
            false
        }
    }
}

//setTextColor(colour)
//textSize = 24.toFloat()
//text = num.toString()
//textAlignment = View.TEXT_ALIGNMENT_GRAVITY
//gravity = Gravity.CENTER
//width = table.layoutParams.width / 15
//height = table.layoutParams.height / 2
//
//when (colour) {  // check colours and then set the border around the textview to that colour
//    BLACK -> setBackgroundResource(R.drawable.black_edge)
//    RED -> setBackgroundResource(R.drawable.red_edge)
//    YELLOW -> setBackgroundResource(R.drawable.yellow_edge)
//    BLUE -> setBackgroundResource(R.drawable.blue_edge)
//}
