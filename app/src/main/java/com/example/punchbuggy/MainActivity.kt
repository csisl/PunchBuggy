package com.example.punchbuggy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var playerListView: ListView
    private lateinit var playerViewAdapter: ArrayAdapter<String>
    private lateinit var runningGame: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readPreferences()
//        runningGame = Game()


        // A game exists, so load the users into a list view
        if (savedInstanceState != null) {
            TODO()
        }

        val gameIntent = getIntent().getStringExtra("runningGame")
        if (gameIntent != null) {
            val gson = Gson()
            val game = gson.fromJson(gameIntent, Game::class.java)
            val players = game.getPlayers()
            for (player in players) {
                runningGame.addPlayer(player)
                Log.d("playerInstance", "player: ${player.username}")
                Log.d("gameInstance", "Adding players to game instance: ${runningGame}")
            }
        }

        playerListView = findViewById(R.id.playerListView)
        playerViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, runningGame.getPlayerNames())
        playerListView.adapter = playerViewAdapter

        val toUserManagement: Button = findViewById(R.id.userManagment)
        toUserManagement.setOnClickListener {
            val toUserManagementIntent = Intent(this, UserManagement::class.java)
            val gson = Gson()
            Log.d("gameInstance", "Passing Game object to user management: ${runningGame}")
            val gameIntentExtra = gson.toJson(runningGame)
            toUserManagementIntent.putExtra("runningGame", gameIntentExtra)
            startActivity(toUserManagementIntent)
        }

        val toUser: Button = findViewById(R.id.viewGames)
        toUser.setOnClickListener {
            val toUserView = Intent(this, UserView::class.java)
            startActivity(toUserView)
        }
    }

    override fun onResume() {
        super.onResume()
        readPreferences()
    }

    override fun onPause() {
        super.onPause()
        writePreferences()
    }

    override fun onStop() {
        super.onStop()
        writePreferences()
    }

    override fun onDestroy() {
        super.onDestroy()
        writePreferences()
    }

    private fun readPreferences() {
        val context = this@MainActivity
        val sharedPref = context?.getPreferences(Context.MODE_PRIVATE)
        val gameData = sharedPref.getString("game", "")
        if (gameData != "") {
            val gson = Gson()
            runningGame = gson.fromJson(gameData, Game::class.java)
            Log.d("gameInstance", "Got Game isntance from shared preference ${runningGame}")
            println(runningGame)
            runningGame.displayScore()
        } else {
            runningGame = Game()
            Log.d("gameInstance", "Made new game instance in Main ${runningGame}")
        }
    }

    private fun writePreferences() {
        val context = this@MainActivity
        val sharedPref = context?.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            val gson = Gson()
            val gameData = gson.toJson(runningGame)
            putString("game", gameData)
            apply()
        }
    }
}