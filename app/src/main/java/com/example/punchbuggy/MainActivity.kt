package com.example.punchbuggy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
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

        val gameIntent = getIntent().getStringExtra("runningGame")
        if (gameIntent != null) {
            val gson = Gson()
            runningGame = gson.fromJson(gameIntent, Game::class.java)
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
            Log.d("gameInstance", "Got Game instance from shared preference ${runningGame}")
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