package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityMainBinding
import android.view.View

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    enum class Turn {
        X, O
    }
    private var firstTurn = Turn.X
    private var currentTurn = Turn.X
    private lateinit var boardList: MutableList<Button>
    private var gameOver = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()
    }

    private fun initBoard() {
        boardList = mutableListOf(
            binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6,
            binding.button7, binding.button8, binding.button9
        )
    }

    fun boardTap(view: View) {
        if (view !is Button || gameOver) return
        addToBoard(view)
        when {
            checkWinner(Turn.O) -> {
                binding.gameStatus.text = "O wins"
                gameOver = true
            }
            checkWinner(Turn.X) -> {
                binding.gameStatus.text = "X wins"
                gameOver = true
            }
            fullBoard() -> {
                binding.gameStatus.text = "Draw"
                gameOver = true
            }
        }
    }

    private fun checkWinner(turn: Turn): Boolean {
        val symbol = turn.name
        return (boardList[0].text == symbol && boardList[1].text == symbol && boardList[2].text == symbol) ||
                (boardList[3].text == symbol && boardList[4].text == symbol && boardList[5].text == symbol) ||
                (boardList[6].text == symbol && boardList[7].text == symbol && boardList[8].text == symbol) ||
                (boardList[0].text == symbol && boardList[3].text == symbol && boardList[6].text == symbol) ||
                (boardList[1].text == symbol && boardList[4].text == symbol && boardList[7].text == symbol) ||
                (boardList[2].text == symbol && boardList[5].text == symbol && boardList[8].text == symbol) ||
                (boardList[0].text == symbol && boardList[4].text == symbol && boardList[8].text == symbol) ||
                (boardList[2].text == symbol && boardList[4].text == symbol && boardList[6].text == symbol)
    }

    private fun fullBoard(): Boolean {
        return boardList.all { it.text.isNotEmpty() }
    }

    fun resetGame(view: View) {
        for (button in boardList) {
            button.text = ""
        }
        currentTurn = firstTurn
        gameOver = false
        setTurnLabel()
    }

    private fun addToBoard(button: Button) {
        if (button.text.isNotEmpty()) return
        button.text = currentTurn.name
        currentTurn = if (currentTurn == Turn.O) Turn.X else Turn.O
        setTurnLabel()
    }

    private fun setTurnLabel() {
        binding.gameStatus.text = if (currentTurn == Turn.O) "O's turn" else "X's turn"
    }
}