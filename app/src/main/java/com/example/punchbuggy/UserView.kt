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
        val resultView: TextView = findViewById(R.id.redCount)
        val redBuddy: ImageButton = findViewById(R.id.redBuggy)
        val homeButton: Button = findViewById(R.id.homeButton)

        // TODO: create a game somewhere, and THERE instantiate all of the punch buggies
        val red = PunchBuggy("red")
        redBuddy.setOnClickListener {
            incrementBuggyCount(red, resultView)
        }
        redBuddy.setOnLongClickListener {
            decrementBuggyCount(red, resultView)
        }

        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            startActivity(homeIntent)
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