package com.punchbuggy.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class UserManagement : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var runningGame: Game
    private lateinit var userExistsWarning: TextView
    private lateinit var users: MutableList<String>

    /**
     * Get the current game intent from the Main Activity
     * and set it to `runningGame`
     */
    private fun setCurrentGame() {
        val gameIntent = intent.getStringExtra("runningGame")

        if (gameIntent != null) {
            val gson = Gson()
            runningGame = gson.fromJson(gameIntent, Game::class.java)
            users = runningGame.getPlayerNames()
        } else {
            runningGame = Game()
        }
    }

    /** Handle the remove user button press */
    private fun removeUserButtonClick(adapter: ArrayAdapter<String>) {
        val removeUserButton: Button = findViewById(R.id.removeUser)
        removeUserButton.setOnClickListener {
            val userInput: EditText = findViewById(R.id.userNameEditText)
            val name = userInput.text.toString()
            users.remove(name)
            runningGame = removePlayerFromGame(name, runningGame)
            adapter.notifyDataSetChanged()
            userInput.setText("")
        }
    }

    /**
     * Remove the given player from the game
     *
     * @param username: the user to remove from the game
     * @param runningGame: the instance of the current running game
     *
     * @return
     *   An updated instance of the running game once the player
     *   is removed
     */
    private fun removePlayerFromGame(username: String, runningGame: Game): Game {
        runningGame.removePlayer(username)
        return runningGame
    }

    /** Handle the add user button press */
    private fun addUserButtonClick(adapter: ArrayAdapter<String>) {
        val addUserButton: Button = findViewById(R.id.addUser)
        addUserButton.setOnClickListener {
            val userInput: EditText = findViewById(R.id.userNameEditText)
            val name = userInput.text.toString()
            runningGame = addPlayerToGame(name, runningGame)
            adapter.notifyDataSetChanged()
            userInput.setText("")
        }
    }

    /**
     * Add the player that was entered into the TextView to the game instance.
     * If the player is already registered, alert the user and do nothing.
     *
     * @param username: The name of the player
     * @param runningGame: The instance of the current game that will be updated
     *
     * @returns
     *  An updated instance of the current game
     */
    private fun addPlayerToGame(username: String, runningGame: Game): Game {
        if (runningGame.hasPlayer(username)) {
            displayUserExists(username)
            return runningGame
        }
        val player = Player(username)
        runningGame.addPlayer(player)
        users.add(username)
        clearUserExistsWarning()
        return runningGame
    }

    /**
     * Save all users to the game and then launch the Main
     * Activity
     *
     * Because the Game object contains a hash map, we have
     * to use an adapter to serialize the object.
     */
    private fun saveUserButtonClick() {
        val saveUsersButton: ImageButton = findViewById(R.id.saveUsers)
        saveUsersButton.setOnClickListener {
            val toMain = Intent(this, MainActivity::class.java)
            val adapter = RuntimeTypeAdapterFactory
                    .of(Game::class.java).registerSubtype(Game::class.java)
            val gson = GsonBuilder().registerTypeAdapterFactory(adapter).create()
            val gameGson = gson.toJson(runningGame, Game::class.java)
            toMain.putExtra("runningGame", gameGson)
            startActivity(toMain)
        }
    }

    /**
     * Set the userExistsWarning text field to alert the user that
     * the player they attempted to save already is in the system
     *
     * @param username:  the username of the player that exists
     */
    private fun displayUserExists(username: String) {
        userExistsWarning = findViewById(R.id.userExistsWarning)
        val text = "User $username already exists!"
        userExistsWarning.text = text
    }

    /** Clear the warning of the TextView, userExistsWarning */
    private fun clearUserExistsWarning() {
        userExistsWarning = findViewById(R.id.userExistsWarning)
        userExistsWarning.text = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        listView = findViewById(R.id.userListView)
        users = mutableListOf()

        setCurrentGame()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        listView.adapter = adapter

        removeUserButtonClick(adapter)

        addUserButtonClick(adapter)

        saveUserButtonClick()
    }

}