package com.punchbuggy.game

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var playerListView: ListView
    private lateinit var playerViewAdapter: ArrayAdapter<String>
    private lateinit var runningGame: Game

    private fun getIntent(intentName: String): String? {
        val intent = intent.getStringExtra(intentName)
        return intent;
    }

    /**
     * Save the current game by writing to the Shared Preferences
     * file
     *
     * @param gameIntent: The intent of the running game
     */
    private fun saveGameInstance(gameIntent: String?) {
        if (gameIntent != null) {
            // Because of the HashMap inside of the Player object, we need
            // to create an Adapter for the serialization
            val adapter = RuntimeTypeAdapterFactory
                    .of(Game::class.java).registerSubtype(Game::class.java)
            val gson = GsonBuilder().registerTypeAdapterFactory(adapter).create()
            runningGame = gson.fromJson(gameIntent, Game::class.java)
            writePreferences()
        }
    }

    /**
     * Initialize the list view of all of the players in the
     * current game
     */
    private fun initPlayerListView() {
        playerListView = findViewById(R.id.playerListView)
        playerViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,
                runningGame.getPlayerNames())
        playerListView.adapter = playerViewAdapter
    }

    /**
     * Launch the Player Activity
     *
     * @param itemClicked: the string of the players
     *   name that was selected
     */
    private fun toPlayerActivity(itemClicked: String) {
        val player: Player? = runningGame.getPlayer(itemClicked)
        val playerIntent = Intent(this, UserView::class.java)
        val playerGson = Gson().toJson(player)
        playerIntent.putExtra("player", playerGson)
        startActivity(playerIntent)
    }

    /**
     * When there are changes made to the players in the current
     * game, update the list on the main screen to reflect
     * those changes
     */
    private fun updatePlayerListView() {
        val playerIntent = intent.getStringExtra("player")
        if (playerIntent != null) {
            val updatedPlayer = Gson().fromJson(playerIntent, Player::class.java)
            runningGame.updatePlayer(updatedPlayer)
            writePreferences()
            runningGame.sortPlayers()
            playerViewAdapter.notifyDataSetChanged()
        }
    }

    /**
     * Load the User Management activity when the button
     * is pressed. Pass the runningGame object to that
     * activity so it can be updated
     */
    private fun toUserManagement() {
        val toUserManagementIntent = Intent(this, UserManagement::class.java)
        val gson = Gson()
        val gameIntentExtra = gson.toJson(runningGame)
        toUserManagementIntent.putExtra("runningGame", gameIntentExtra)
        startActivity(toUserManagementIntent)
    }

    private fun toHelp() {
        val toHelpIntent = Intent(this, Help::class.java)
        startActivity(toHelpIntent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        readPreferences()

        val gameIntent = getIntent("runningGame")
        saveGameInstance(gameIntent)

        // initialize the main list view that shows all players
        initPlayerListView()

        // a listener for the list of players, load the player page when clicked
        playerListView.setOnItemClickListener { _, _, position, _ ->
            val clicked = playerViewAdapter.getItem(position).toString()
            toPlayerActivity(clicked)
        }

        updatePlayerListView()

        // Load the user management page
        val toUserManagement: Button = findViewById(R.id.userManagment)
        toUserManagement.setOnClickListener {
            toUserManagement()
        }

        // load the help page
        val toHelp: ImageButton = findViewById(R.id.helpButton)
        toHelp.setOnClickListener {
            toHelp()
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

    /*
     * Read the shared preferences file and instantiate the runningGame
     * variable that is an instance of the current game
     */
    private fun readPreferences() {
        val context = this@MainActivity
        val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
        val gameData = sharedPref.getString("game", "")
        if (gameData != "") {
            val gson = Gson()
            runningGame = gson.fromJson(gameData, Game::class.java)
        } else {
            runningGame = Game()
        }
    }

    /*
     * Save the current state of the game by writing it to a
     * shared preferences file
     */
    private fun writePreferences() {
        val context = this@MainActivity
        val sharedPref = context.getPreferences(Context.MODE_PRIVATE)
        with (sharedPref.edit()) {
            val gson = Gson()
            val gameData = gson.toJson(runningGame)
            putString("game", gameData)
            apply()
        }
    }

}