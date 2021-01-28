package com.example.punchbuggy

import android.content.Context
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

    private lateinit var player: Player
    private lateinit var redResultView: TextView
    private lateinit var orangeResultView: TextView
    private lateinit var yellowResultView: TextView
    private lateinit var greenResultView: TextView
    private lateinit var blueResultView: TextView
    private lateinit var purpleResultView: TextView
    private lateinit var pinkResultView: TextView
    private lateinit var silverResultView: TextView
    private lateinit var blackResultView: TextView
    private lateinit var whiteResultView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)

        val playerData = getIntent().getStringExtra("player")

        if (playerData != null) {
            val gson = Gson()
            player = gson.fromJson(playerData, Player::class.java)
        } else {
            player = Player("null")
        }

        val redBuddy: ImageButton = findViewById(R.id.redBuggy)
        redResultView = findViewById(R.id.redCount)

        val orangeBuddy: ImageButton = findViewById(R.id.orangeBuggy)
        orangeResultView = findViewById(R.id.orangeCount)

        val yellowBuddy: ImageButton = findViewById(R.id.yellowBuggy)
        yellowResultView = findViewById(R.id.yellowCount)

        val greenBuddy: ImageButton = findViewById(R.id.greenBuggy)
        greenResultView = findViewById(R.id.greenCount)

        val blueBuddy: ImageButton = findViewById(R.id.blueBuggy)
        blueResultView = findViewById(R.id.blueCount)

        val purpleBuddy: ImageButton = findViewById(R.id.purpleBuggy)
        purpleResultView = findViewById(R.id.purpleCount)

        val pinkBuddy: ImageButton = findViewById(R.id.pinkBuggy)
        pinkResultView = findViewById(R.id.pinkCount)

        val silverBuddy: ImageButton = findViewById(R.id.silverBuggy)
        silverResultView = findViewById(R.id.silverCount)

        val blackBuddy: ImageButton = findViewById(R.id.blackBuggy)
        blackResultView = findViewById(R.id.blackCount)

        val whiteBuddy: ImageButton = findViewById(R.id.whiteBuggy)
        whiteResultView = findViewById(R.id.whiteCount)

        populateUserData(player)

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

        val saveButton: ImageButton = findViewById(R.id.saveButton)
        saveButton.setOnClickListener {
            val homeIntent = Intent(this, MainActivity::class.java)
            val playerGson = Gson().toJson(player)
            homeIntent.putExtra("player", playerGson)
            startActivity(homeIntent)
        }

    }

    private fun populateUserData(player: Player) {

        Log.d("populateUserData", "Player current score for red: ${player.getScoreForColor("red")}")

        redResultView.setText(player.getScoreForColor("red").toString())
        orangeResultView.setText(player.getScoreForColor("orange").toString())
        yellowResultView.setText(player.getScoreForColor("yellow").toString())
        greenResultView.setText(player.getScoreForColor("green").toString())
        blueResultView.setText(player.getScoreForColor("blue").toString())
        purpleResultView.setText(player.getScoreForColor("purple").toString())
        pinkResultView.setText(player.getScoreForColor("pink").toString())

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