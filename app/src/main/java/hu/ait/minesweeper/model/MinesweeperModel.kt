package hu.ait.minesweeper.model

import java.lang.IndexOutOfBoundsException


object MinesweeperModel {

    private val model = ArrayList<ArrayList<Field>>()
    var loser : Boolean = false

    init {
        for (x in 0 until 5) {
            var row = ArrayList<Field>()
            for (y in 0 until 5) {
                row.add(Field(false, false, 0, false, x, y))
            }
            model.add(row)
        }
    }

    fun getFieldAtIndex(x : Int, y : Int) : Field {
        return model[x][y]
    }

    fun toggleFlagged(x : Int, y : Int) {
        model[x][y].toggleFlagged()
    }

    fun generateMines() {
        var availableMines = 3

        while (availableMines != 0) {
            var choiceX = (0 until 5).random()
            var choiceY = (0 until 5).random()
            if (!model[choiceX][choiceY].hasMine) {
                model[choiceX][choiceY].hasMine = true
                availableMines -= 1
            }
        }
    }

    fun countNearbyMines() {
        for (row in model) {
            for (field in row) {
                if (field.hasMine) {
                    var neighbours = getNeighbourFields(field.x, field.y)
                    for (item in neighbours) {
                        item.adjacentMines += 1
                    }
                }
            }
        }
    }

    private fun getNeighbourFields(x : Int, y : Int) : ArrayList<Field> {
        var neighbours = ArrayList<Field>()

            for (i in -1..1) {
                for (j in -1..1) {
                    try {
                        neighbours.add(getFieldAtIndex(x+i, y+j))
                    } catch (e: IndexOutOfBoundsException) {
                    }
                }
            }
            neighbours.remove(getFieldAtIndex(x, y))
        return neighbours
    }

    fun checkWin() : Boolean {
        var flaggedMines = 0
        for (x in 0 until 5) {
            for (y in 0 until 5) {
                if (model[x][y].isFlagged && model[x][y].hasMine) {
                    flaggedMines += 1
                }
            }
        }
        if (flaggedMines == 3) {
            return true
        }
        return false
    }

    fun reset() {
        model.clear()
        for (x in 0 until 5) {
            var row = ArrayList<Field>()
            for (y in 0 until 5) {
                row.add(Field(false, false, 0, false, x, y))
            }
            model.add(row)
        }
        loser = false
        generateMines()
        countNearbyMines()
    }
}