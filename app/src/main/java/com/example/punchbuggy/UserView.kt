package com.example.punchbuggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.gson.Gson

class UserView : AppCompatActivity() {
    // TODO: populate the text view of the different counts when first loaded
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)

        val playerData = getIntent().getStringExtra("player")
        val gson = Gson()
        val player = gson.fromJson(playerData, Player::class.java)

        val redResultView: TextView = findViewById(R.id.redCount)
        val redBuddy: ImageButton = findViewById(R.id.redBuggy)

        val orangeBuddy: ImageButton = findViewById(R.id.orangeBuggy)
        val orangeResultView: TextView = findViewById(R.id.orangeCount)

        val yellowBuddy: ImageButton = findViewById(R.id.yellowBuggy)
        val yellowResultView: TextView = findViewById(R.id.yellowCount)

        val greenBuddy: ImageButton = findViewById(R.id.greenBuggy)
        val greenResultView: TextView = findViewById(R.id.greenCount)

        val blueBuddy: ImageButton = findViewById(R.id.blueBuggy)
        val blueResultView: TextView = findViewById(R.id.blueCount)

        val purpleBuddy: ImageButton = findViewById(R.id.purpleBuggy)
        val purpleResultView: TextView = findViewById(R.id.purpleCount)

        val pinkBuddy: ImageButton = findViewById(R.id.pinkBuggy)
        val pinkResultView: TextView = findViewById(R.id.pinkCount)

        val silverBuddy: ImageButton = findViewById(R.id.silverBuggy)
        val silverResultView: TextView = findViewById(R.id.silverCount)

        val blackBuddy: ImageButton = findViewById(R.id.blackBuggy)
        val blackResultView: TextView = findViewById(R.id.blackCount)

        val whiteBuddy: ImageButton = findViewById(R.id.whiteBuggy)
        val whiteResultView: TextView = findViewById(R.id.whiteCount)

        val homeButton: Button = findViewById(R.id.homeButton)
        homeButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            val playerGson = Gson().toJson(player)
            homeIntent.putExtra("player", playerGson)
            startActivity(homeIntent)
        }

        redBuddy.setOnClickListener {
            incrementBuggyCount(player, "red", redResultView)
        }
        redBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "red", redResultView)
        }

        orangeBuddy.setOnClickListener {
            incrementBuggyCount(player, "orange", orangeResultView)
        }
        orangeBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "orange", orangeResultView)
        }

        yellowBuddy.setOnClickListener {
            incrementBuggyCount(player, "yellow", yellowResultView)
        }
        yellowBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "yellow", yellowResultView)
        }

        greenBuddy.setOnClickListener {
            incrementBuggyCount(player, "green", greenResultView)
        }
        greenBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "green", greenResultView)
        }

        blueBuddy.setOnClickListener {
            incrementBuggyCount(player, "blue", blueResultView)
        }
        blueBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "blue", blueResultView)
        }

        purpleBuddy.setOnClickListener {
            incrementBuggyCount(player, "purple", purpleResultView)
        }
        purpleBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "purple", purpleResultView)
        }

        pinkBuddy.setOnClickListener {
            incrementBuggyCount(player, "pink", pinkResultView)
        }
        pinkBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "pink", pinkResultView)
        }

        silverBuddy.setOnClickListener {
            incrementBuggyCount(player, "silver", silverResultView)
        }
        silverBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "silver", silverResultView)
        }

        whiteBuddy.setOnClickListener {
            incrementBuggyCount(player, "white", whiteResultView)
        }
        whiteBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "white", whiteResultView)
        }

        blackBuddy.setOnClickListener {
            incrementBuggyCount(player, "black", blackResultView)
        }
        blackBuddy.setOnLongClickListener {
            decrementBuggyCount(player, "black", blackResultView)
        }

    }

    private fun incrementBuggyCount(player: Player, color: String, resultView: TextView) {
        player.addPoint(color)
        resultView.text = player.getCurrentPunchBuggy(color).getCount().toString()
    }

    private fun decrementBuggyCount(player: Player, color: String, resultView: TextView): Boolean {
        player.getCurrentPunchBuggy(color).decrementCount()
        resultView.text = player.getCurrentPunchBuggy(color).getCount().toString()
        return true
    }
}