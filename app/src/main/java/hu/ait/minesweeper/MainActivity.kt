package hu.ait.minesweeper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ToggleButton
import com.google.android.material.snackbar.Snackbar
import hu.ait.minesweeper.model.MinesweeperModel
import hu.ait.minesweeper.view.MinesweeperView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetBtn.setOnClickListener {
            MinesweeperModel.reset()
        }
    }

    fun gameResult(winner : Boolean) {
        if (winner) {
            var snack = Snackbar.make(rootLayout, "You won!", Snackbar.LENGTH_LONG)
            snack.setAction("Play Again", View.OnClickListener {
                MinesweeperModel.reset()
            })
            snack.show()
        } else {
            var snack = Snackbar.make(rootLayout, "You lost!", Snackbar.LENGTH_LONG)
            snack.setAction("Play Again", View.OnClickListener {
                MinesweeperModel.reset()
            })
            snack.show()
        }
    }

    fun getToggleButtonState() : Boolean {
        return toggle.isChecked
    }
}
