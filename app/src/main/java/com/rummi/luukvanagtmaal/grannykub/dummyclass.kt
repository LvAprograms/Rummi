//package com.rummi.luukvanagtmaal.grannykub

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.main.activity_play.*



//class PlayActivity : AppCompatActivity(), View.OnClickListener, View.OnTouchListener {
//    val COLOURS: List<Int> = listOf(BLUE, RED, YELLOW, BLACK)
//    val NUMBERS = IntRange(1, 13)
//    lateinit var nameTv: TextView
//    lateinit var player: Player
//    lateinit var plankGrid: TableLayout
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_play)
//        plankGrid = findViewById(R.id.plankGrid)
//        player = Player("Jack", plankGrid)
//        val pack: MutableList<Tile> = makeTiles()
//        nameTv = findViewById(R.id.nameTextView)
//        nameTv.text = player.name
//        player.drawTiles(14, pack)
//
//        // OnClickListeners for the buttons in this Activity
//        val sortBut = findViewById<Button>(R.id.sortButton)
//        sortBut.setOnClickListener {
//            onClick(sortBut)
//        }
//        val hideBtn: Button = findViewById(R.id.hideBtn)
//        hideBtn.setOnClickListener {
//            onClick(hideBtn)
//        }
//
//        //OnTouchListener for tiles
//        val plankGrid: TableLayout = findViewById(R.id.plankGrid)
//        for (i: Int in 0 until plankGrid.childCount) {
//            val row: TableRow = plankGrid.getChildAt(i) as TableRow
//            for (j:Int in 0 until row.childCount) {
//                val v: View = row.getChildAt(j)
//                v.setOnTouchListener { view, motionEvent ->
//                    onTouch(view, motionEvent)
//                }
//            }
//        }
//
//    }
//
//
//    private fun makeTiles(): MutableList<Tile> {
//        val a = mutableListOf<Tile>()
//        var count = 0
//        for (value in COLOURS) {
//            for (number in NUMBERS) {
//                count += 1
//                val tile = Tile(this)
//                tile.setup(plankGrid, value, number)
//                a.add(tile)
//            }
//        }
//        a.shuffle()
//        return a
//    }
//
//    override fun onClick(view: View) {
//        when (view.id) {
//            R.id.sortButton -> {
//                player.sortPlank()
//            }
//            R.id.hideBtn -> {
//                val plank: TableLayout = findViewById(R.id.plankGrid)
//                val strArr: Array<String> = resources.getStringArray(R.array.hideBtnTxt)
//                when (hideBtn.text) {
//                    strArr[0] -> {
//                        hideBtn.text = strArr[1]
//                        plank.visibility = View.GONE
//                    }
//                    strArr[1] -> {
//                        hideBtn.text = strArr[0]
//                        plank.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
//        if (view == null) return false
//        val x: Int = event?.rawX!!.toInt() //?: -1f  absolute position of touch event
//        val testx: Float = event.x // relative position of touch event
//        val testy: Float = event.y
//        val y: Int = event.rawY.toInt() //?: -1f
//        val viewPos = IntArray(2)
//        view.getLocationOnScreen(viewPos) // absolute position of the tile on screen
//        val viewX: Int = viewPos[0]
//        val viewY: Int = viewPos[1]
//
//        when (event.action) {
//            MotionEvent.ACTION_DOWN -> {
//                Log.d(this.toString(), "testx: $testx, x: $x, testy: $testy, y: $y")
////                    TODO("Detach tile from row so it can move freely between rows")
//                // detach tile from row
//                val currentrow = view.parent as TableRow
//                val pos = listOf(viewX, viewY)
//                currentrow.removeView(view)
//                view.x = pos[0].toFloat(); view.y = pos[1].toFloat()
//                return true
//            }
//            MotionEvent.ACTION_MOVE -> {
//                view.performClick()
//                if (viewX <= x && x <= viewX + view.width && viewY <= y && viewY <= y + view.height) {
////                                Log.d(this.toString(),"oldX: $oldX, x: $x, view.x: ${view.x}, result: ${x - view.x}")
//                    Toast.makeText(this,"$viewX,${view.x}", Toast.LENGTH_SHORT).show()
//                    view.x += (x - (viewX + 0.5 * view.width)).toFloat()
//                    view.y += (y - (viewY + 0.5 * view.height)).toFloat()
//                    return true
//                }
//
//            }
////                MotionEvent.ACTION_UP ->  {
////                    view.performClick()
////                    //Find closest Tile and swap positions with that one preferably highlighting which one will be swapped
////                    for (i in 0 until plankGrid.childCount) {
////                        val row: TableRow = plankGrid.getChildAt(i) as TableRow
////                        for (j in 0 until row.childCount) {
////                            val child = row.getChildAt(j) as Tile
////                            if (child.x <= view.x && view.x <= child.x + child.width) {
////                                child.swapPosition(view as Tile, viewX, viewY)
////                                return true
////                            }
////                        }
////                    }
////                }
//        }
//        return true
//    }
//
//}
//

