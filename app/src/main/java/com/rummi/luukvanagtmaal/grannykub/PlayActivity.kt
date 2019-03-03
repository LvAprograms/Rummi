package com.rummi.luukvanagtmaal.grannykub

import android.content.ClipData
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.*
import android.widget.*
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.activity_play.view.*
import kotlin.math.ceil
import kotlin.math.roundToInt


class PlayActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener, View.OnDragListener {
    val COLOURS: List<Int> = listOf(BLUE, RED, YELLOW, BLACK)
    val NUMBERS = IntRange(1, 13)
    lateinit var nameTv: TextView
    lateinit var player: Player
    lateinit var plankGrid: TableLayout
    lateinit var board: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        plankGrid = findViewById(R.id.plankGrid)
        player = Player("Jack", plankGrid)
        val pack: MutableList<Tile> = makeTiles()
        nameTv = findViewById(R.id.nameTextView)
        nameTv.text = player.name
        player.drawTiles(14, pack)

        // OnClickListeners for the buttons in this Activity
        val sortBut = findViewById<Button>(R.id.sortButton)
        sortBut.setOnClickListener {
            onClick(sortBut)
        }
        val hideBtn: Button = findViewById(R.id.hideBtn)
        hideBtn.setOnClickListener {
            onClick(hideBtn)
        }

        //OnTouchListener for tiles
        plankGrid = findViewById(R.id.plankGrid)
        for (i: Int in 0 until plankGrid.childCount) {
            val row: TableRow = plankGrid.getChildAt(i) as TableRow
            for (j:Int in 0 until row.childCount) {
                val v: View = row.getChildAt(j)
                v.setOnTouchListener { view, motionEvent ->
                    onTouch(view, motionEvent)
                }
            }
        }

        //OnDragListeners for the root layout and the plank
        board = findViewById(R.id.BoardFrame)
        board.setOnDragListener { view, dragEvent ->
            onDrag(view, dragEvent)
        }

        plankGrid.setOnDragListener { view, dragEvent ->
            onDrag(view, dragEvent)
        }

    }


    private fun makeTiles(): MutableList<Tile> {
        val a = mutableListOf<Tile>()
        var count = 0
        for (value in COLOURS) {
            for (number in NUMBERS) {
                count += 1
                val tile = Tile(this)
                tile.setup(plankGrid, value, number)
                a.add(tile)
            }
        }
        a.shuffle()
        return a
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.sortButton -> {
                Log.d(this.toString(), "Sort button pressed")
                player.sortPlank()
            }
            R.id.hideBtn -> {
                val plank: TableLayout = findViewById(R.id.plankGrid)
                val strArr: Array<String> = resources.getStringArray(R.array.hideBtnTxt)
                when (hideBtn.text) {
                    strArr[0] -> {
                        hideBtn.text = strArr[1]
                        plank.visibility = View.GONE
                    }
                    strArr[1] -> {
                        hideBtn.text = strArr[0]
                        plank.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        if (view == null) return false
            val x: Int = event?.rawX!!.toInt() //?: -1f  absolute position of touch event
            val y: Int = event.rawY.toInt() //?: -1f
            val viewPos = IntArray(2)
            view.getLocationOnScreen(viewPos) // absolute position of the tile on screen
            val viewX: Int = viewPos[0]
            val viewY: Int = viewPos[1]

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
               val data: ClipData = ClipData.newPlainText("","")
                val shadowBuilder: View.DragShadowBuilder = View.DragShadowBuilder(view)
                view.startDrag(data, shadowBuilder, view, 0)
                view.visibility = View.INVISIBLE
                return true
            }
            MotionEvent.ACTION_UP -> {

            }

            }
        return true
    }

    override fun onDrag(v: View?, event: DragEvent?): Boolean {
        if (v == null) return false
        val tile = event?.localState as Tile
        when (event.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                }

                DragEvent.ACTION_DRAG_ENTERED -> {
                    // might highlight view to indicate that it can be dropped
//                    v.setBackgroundResource(R.drawable.highlight)
//                    v.invalidate()
                }

                DragEvent.ACTION_DRAG_EXITED -> {
                    // make the highlight disappear again
                    if (v is Tile) {
                        Log.d(this.toString(), "resetting background colour")
                        when (v.getProp("colour")) {
                            BLACK -> {
                                v.setBackgroundResource(R.drawable.black_edge)
                            }
                            RED -> {
                                v.setBackgroundResource(R.drawable.red_edge)
                            }
                            BLUE -> {
                                v.setBackgroundResource(R.drawable.blue_edge)
                            }
                            YELLOW -> {
                                v.setBackgroundResource(R.drawable.yellow_edge)
                            }
                        }
                        v.invalidate()
                    }
                }

                DragEvent.ACTION_DROP -> {
                    // Most important one!
                    val owner: ViewGroup = tile.parent as TableRow
                    val row1 = findViewById<TableRow>(R.id.row1)
                    val row2 = findViewById<TableRow>(R.id.row2)
                    if (tile.within(plankGrid, event)) {
                        Log.d(this.toString(), "tile is within plankgrid, x, y = ${event.x + plankGrid.width},  ${event.y + plankGrid.height}")
                        var swapTargetFound = false
                        for (i in 0 until plankGrid.childCount) {
                            val row: TableRow = plankGrid.getChildAt(i) as TableRow
                            if (row.childCount != 0) {
                                var j = 0
                                while (!swapTargetFound && j < row.childCount) {  // means: while swapTargetFound = false
                                    val t: Tile = row.getChildAt(j) as Tile
                                    j += 1
                                    if (t.findSwapTarget(tile, event)) {
                                        swapTargetFound = true
                                        if (t != tile) { // to avoid swapping with itself
                                            tile.swapPosition(t, owner, row)
                                            tile.invalidate()
                                        }
                                    }
                                }
                            }

                        }
                        // If after iteration no target has been found, the user must have dragged it to an empty socket
                        if (!swapTargetFound) {
                            // determine on which "spot" the tile has to be placed
                            Log.d(this.toString(), "switching tile with empty socket")
                            val newX = (ceil(event.x / tile.width)).toInt()
                            val newY = (ceil(event.y / tile.height)).toInt()
                            if (newY > 1 && owner.id != row2.id) {
                                Log.d(this.toString(), "moving tile from row1 to row2")
                                owner.removeView(tile)
                                row2.addView(tile)
                                Log.d(this.toString(), "tile.x: ${tile.x}, tile.y: ${tile.y}")
                            }
                            else if (newY == 1 && owner.id != row1.id) {
                                Log.d(this.toString(), "moving tile from row2 to row1")
                                owner.removeView(tile)
                                row1.addView(tile)
                                Log.d(this.toString(), "tile.x: ${tile.x}, tile.y: ${tile.y}")
                            }
                            tile.y = 0f
                            tile.x = ((newX - 1) * tile.width).toFloat()
                            Log.d(this.toString(), "tile.x: ${tile.x}, tile.y: ${tile.y}")
                            tile.visibility = View.VISIBLE
                            tile.invalidate()
                        }



                        // just to check if the idea works (it does)
//                        val firstRow: TableRow = plankGrid.getChildAt(0) as TableRow
//                        val dummyTile: Tile = firstRow.getChildAt(0) as Tile
//                        tile.swapPosition(dummyTile)
//                        tile.invalidate()

                    }
                    // if not within plankGrid, it has to be in the board.
                    else {
                        owner.removeView(tile)
                        player.plankTiles.remove(tile)
                        board.addView(tile)
                        tile.layoutParams = FrameLayout.LayoutParams(tile.width, tile.height)
                        (tile.layoutParams as FrameLayout.LayoutParams).gravity = Gravity.NO_GRAVITY
                        tile.x = event.x - tile.width / 2
                        tile.y = event.y - tile.height / 2
                        tile.visibility = View.VISIBLE
                        player.count -= 1
                    }

                }

                DragEvent.ACTION_DRAG_ENDED -> {
                    if (event.result) {
                        tile.visibility = View.VISIBLE // To make sure the tiles don't disappear!
                    }
                }

            }
        return true
    }

}


