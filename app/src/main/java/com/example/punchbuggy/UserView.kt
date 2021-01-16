package com.example.punchbuggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

class UserView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)
        val redResultView: TextView = findViewById(R.id.redCount)
        val redBuddy: ImageButton = findViewById(R.id.redBuggy)
        val orangeBuddy: ImageButton = findViewById(R.id.orangeBuggy)
        val orangeResultView: TextView = findViewById(R.id.orangeCount)

        val red = PunchBuggy("red")
        redBuddy.setOnClickListener {
            incrementBuggyCount(red, redResultView)
        }
        redBuddy.setOnLongClickListener {
            decrementBuggyCount(red, redResultView)
        }

        val orange = PunchBuggy("orange")
        orangeBuddy.setOnClickListener {
            incrementBuggyCount(orange, orangeResultView)
        }
        orangeBuddy.setOnLongClickListener {
            decrementBuggyCount(orange, orangeResultView)
        }

    }

    private fun incrementBuggyCount(color: PunchBuggy, resultView: TextView) {
        color.incrementCount()
        resultView.text = color.getCount().toString()
    }

    private fun decrementBuggyCount(color: PunchBuggy, resultView: TextView): Boolean {
        color.decrementCount()
        resultView.text = color.getCount().toString()
        return true
    }
}