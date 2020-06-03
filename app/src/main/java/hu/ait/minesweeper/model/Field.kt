package hu.ait.minesweeper.model

data class Field(var isFlagged : Boolean, var hasMine : Boolean,
                 var adjacentMines : Int, var isRevealed : Boolean, var x : Int, var y : Int) {

    fun toggleFlagged() {
        isFlagged = !isFlagged
    }

    override fun toString(): String {
        return "Nearby Mines: $adjacentMines"
    }

}