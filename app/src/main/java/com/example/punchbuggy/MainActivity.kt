package com.example.punchbuggy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val resultView: TextView = findViewById(R.id.redCount)
        val redBuddy: ImageButton = findViewById(R.id.redBuggy)

        // TODO: create a game somewhere, and THERE instantiate all of the punch buggies
        val red = PunchBuggy("red")
        redBuddy.setOnClickListener {
            incrementBuggyCount(red, resultView)
        }
        redBuddy.setOnLongClickListener {
            decrement(red, resultView)
        }
    }

    private fun incrementBuggyCount(color: PunchBuggy, resultView: TextView) {
        color.incrementCount()
        resultView.text = color.getCount().toString()
    }

    private fun decrement(color: PunchBuggy, resultView: TextView): Boolean {
        color.decrementCount()
        resultView.text = color.getCount().toString()
        return true
    }
}