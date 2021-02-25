package com.example.punchbuggy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var playerListView: ListView
    private lateinit var playerViewAdapter: ArrayAdapter<String>
    private lateinit var runningGame: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readPreferences()

        /*
         *  Get the game intent from the UserManagement activity
         *  This activity adds players to the current game
         */
        val gameIntent = getIntent().getStringExtra("runningGame")
        if (gameIntent != null) {
            val adapter = RuntimeTypeAdapterFactory
                    .of(Game::class.java).registerSubtype(Game::class.java)
//            val gson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create()
            val gson = GsonBuilder().registerTypeAdapterFactory(adapter).create()
            runningGame = gson.fromJson(gameIntent, Game::class.java)
            writePreferences()
        }

        playerListView = findViewById(R.id.playerListView)
        playerViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, runningGame.getPlayerNames())
        playerListView.adapter = playerViewAdapter

        playerListView.setOnItemClickListener { _, _, position, _ ->
            val clicked = playerViewAdapter.getItem(position).toString()
            Log.d("clicked", "clicked: $clicked")
            val player: Player? = runningGame.getPlayer(clicked)

            if (null == player) {

            }

            val playerIntent = Intent(this, UserView::class.java)
            val playerGson = Gson().toJson(player)
            playerIntent.putExtra("player", playerGson)
            startActivity(playerIntent)
        }

        /*
         *  Get the updated player from the UserView and save it to the
         *  running game instance
         */
        val playerIntent = getIntent().getStringExtra("player")
        if (playerIntent != null) {
            val updatedPlayer = Gson().fromJson(playerIntent, Player::class.java)
            runningGame.updatePlayer(updatedPlayer)
            writePreferences()
            runningGame.sortPlayers()
            playerViewAdapter.notifyDataSetChanged()
        }

        val toUserManagement: Button = findViewById(R.id.userManagment)
        toUserManagement.setOnClickListener {
            val toUserManagementIntent = Intent(this, UserManagement::class.java)
            val gson = Gson()
            Log.d("gameInstance", "Passing Game object to user management: ${runningGame}")
            val gameIntentExtra = gson.toJson(runningGame)
            toUserManagementIntent.putExtra("runningGame", gameIntentExtra)
            startActivity(toUserManagementIntent)
        }

        val toHelp: ImageButton = findViewById(R.id.helpButton)
        toHelp.setOnClickListener {
            val toHelpIntent = Intent(this, Help::class.java)
            startActivity(toHelpIntent)
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