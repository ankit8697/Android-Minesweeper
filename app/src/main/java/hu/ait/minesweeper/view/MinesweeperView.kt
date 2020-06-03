package hu.ait.minesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.minesweeper.MainActivity
import hu.ait.minesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var paintBackground : Paint = Paint()
    var paintLine : Paint = Paint()
    var paintZero : Paint = Paint()
    var paintOne : Paint = Paint()
    var paintTwo : Paint = Paint()
    var paintThree : Paint = Paint()
    var paintFlag : Paint = Paint()
    var paintMine : Paint = Paint()

    init {
        paintBackground.color = Color.GRAY
        paintBackground.style = Paint.Style.FILL

        paintLine.color = Color.DKGRAY
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 7f
        paintZero.color = Color.BLACK
        paintZero.textSize = 100f
        paintOne.color = Color.BLUE
        paintOne.textSize = 100f

        paintTwo.color = Color.GREEN
        paintTwo.textSize = 100f

        paintThree.color = Color.RED
        paintThree.textSize = 100f

        paintFlag.textSize = 70f

        paintMine.textSize = 70f

        MinesweeperModel.generateMines()
        MinesweeperModel.countNearbyMines()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)
        drawBoard(canvas)
        drawNumbers(canvas)
        drawFlags(canvas)
        if (MinesweeperModel.loser) {
            drawMine(canvas)
        }
    }

    private fun drawBoard(canvas: Canvas?) {
        // border

        val size = 5
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)

        // four horizontal lines
        canvas?.drawLine(
            0f, (height / size).toFloat(), width.toFloat(), (height / size).toFloat(),
            paintLine
        )
        canvas?.drawLine(
            0f, (2 * height / size).toFloat(), width.toFloat(),
            (2 * height / size).toFloat(), paintLine
        )
        canvas?.drawLine(
            0f, (3 * height / size).toFloat(), width.toFloat(),
            (3 * height / size).toFloat(), paintLine
        )
        canvas?.drawLine(
            0f, (4 * height / size).toFloat(), width.toFloat(),
            (4 * height / size).toFloat(), paintLine
        )

        // four vertical lines
        canvas?.drawLine(
            (width / size).toFloat(), 0f, (width / size).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (2 * width / size).toFloat(), 0f, (2 * width / size).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (3 * width / size).toFloat(), 0f, (3 * width / size).toFloat(), height.toFloat(),
            paintLine
        )
        canvas?.drawLine(
            (4 * width / size).toFloat(), 0f, (4 * width / size).toFloat(), height.toFloat(),
            paintLine
        )
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val tX = event.x.toInt() / (width/5)
            val tY = event.y.toInt() / (height/5)

            if ((!MinesweeperModel.getFieldAtIndex(tX, tY).isRevealed)) {
                if ((context as MainActivity).getToggleButtonState()) { // Try the field
                    var field = MinesweeperModel.getFieldAtIndex(tX, tY)
                    field.isRevealed = true
                    if (field.hasMine) {
                        MinesweeperModel.loser = true
                        (context as MainActivity).gameResult(false)
                    }
                } else {
                    var field = MinesweeperModel.getFieldAtIndex(tX, tY)
                    if (!field.hasMine) {
                        field.isFlagged = true
                        (context as MainActivity).gameResult(false)
                    } else {
                        field.isFlagged = true
                        if (MinesweeperModel.checkWin()) {
                            (context as MainActivity).gameResult(true)
                        }
                    }
                }
            }
            invalidate()
        }
        return true
    }

    private fun drawNumbers(canvas: Canvas?) {
        for (x in 0 until 5) {
            for (y in 0 until 5) {
                var field = MinesweeperModel.getFieldAtIndex(x, y)
                if (field.isRevealed && field.adjacentMines == 0 && !field.hasMine) {
                    canvas?.drawText("0", (width * (x.toFloat())/5f) + width/14, (height * (y.toFloat())/5f) + height/7, paintZero)
                }
                else if (field.isRevealed && field.adjacentMines == 1 && !field.hasMine) {
                    canvas?.drawText("1", (width * (x.toFloat())/5f) + width/14, (height * (y.toFloat())/5f) + height/7, paintOne)
                }
                else if (field.isRevealed && field.adjacentMines == 2 && !field.hasMine) {
                    canvas?.drawText("2", (width * (x.toFloat())/5f) + width/14, (height * (y.toFloat())/5f) + height/7, paintTwo)
                }
                else if (field.isRevealed && field.adjacentMines == 3 && !field.hasMine) {
                    canvas?.drawText("3", (width * (x.toFloat())/5f) + width/14, (height * (y.toFloat())/5f) + height/7, paintThree)
                }
            }
        }
    }

    private fun drawFlags(canvas: Canvas?) {
        for (x in 0 until 5) {
            for (y in 0 until 5) {
                var field = MinesweeperModel.getFieldAtIndex(x, y)
                if (!field.isRevealed && field.isFlagged) {
                    canvas?.drawText("\uD83D\uDEA9️", (width * (x.toFloat())/5f) + width/15, (height * (y.toFloat())/5f) + height/7, paintFlag)
                }
            }
        }
    }

    private fun drawMine(canvas: Canvas?) {
        if (MinesweeperModel.loser) {
            for (x in 0 until 5) {
                for (y in 0 until 5) {
                    var field = MinesweeperModel.getFieldAtIndex(x, y)
                    if (field.hasMine) {
                        canvas?.drawText("\uD83D\uDCA3", (width * (x.toFloat())/5f) + width/18, (height * (y.toFloat())/5f) + height/7, paintFlag)
                    }
                }
            }
        }
    }
}