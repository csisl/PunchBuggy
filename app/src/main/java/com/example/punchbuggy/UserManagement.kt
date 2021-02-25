package com.example.punchbuggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class UserManagement : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var runningGame: Game
    private lateinit var userExistsWarning: TextView
    private lateinit var users: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        listView = findViewById(R.id.userListView)
        users = mutableListOf()

        val gameIntent = getIntent().getStringExtra("runningGame")

        if (gameIntent != null) {
            val gson = Gson()
            runningGame = gson.fromJson(gameIntent, Game::class.java)
            users = runningGame.getPlayerNames()
            Log.d("usersAdd", "Users: ${users}")
        } else {
            runningGame = Game()
            Log.d("gameInstance", "Made new game instance in management ${runningGame}")
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        listView.adapter = adapter

        val removeUserButton: Button = findViewById(R.id.removeUser)
        removeUserButton.setOnClickListener {
            val userInput: EditText = findViewById(R.id.userNameEditText)
            val name = userInput.getText().toString()
            users.remove(name)
            runningGame = removePlayerFromGame(name, runningGame)
            adapter.notifyDataSetChanged()
            userInput.setText("")
        }

        val addUserButton: Button = findViewById(R.id.addUser)
        addUserButton.setOnClickListener {
            val userInput: EditText = findViewById(R.id.userNameEditText)
            val name = userInput.getText().toString()
            runningGame = addPlayerToGame(name, runningGame)
            adapter.notifyDataSetChanged()
            userInput.setText("")
        }

        val saveUsersButton: ImageButton = findViewById(R.id.saveUsers)
        saveUsersButton.setOnClickListener {
//            val gson = Gson()
            val toMain = Intent(this, MainActivity::class.java)
            val adapter = RuntimeTypeAdapterFactory
                    .of(Game::class.java).registerSubtype(Game::class.java)
//            val gson = GsonBuilder().setPrettyPrinting().registerTypeAdapterFactory(adapter).create()
            val gson = GsonBuilder().registerTypeAdapterFactory(adapter).create()
            val gameGson = gson.toJson(runningGame, Game::class.java)
            toMain.putExtra("runningGame", gameGson)
            startActivity(toMain)
        }

    }

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

    private fun displayUserExists(username: String) {
        userExistsWarning = findViewById(R.id.userExistsWarning)
        userExistsWarning.setText("User $username already exists!")
    }

    private fun clearUserExistsWarning() {
        userExistsWarning = findViewById(R.id.userExistsWarning)
        userExistsWarning.setText("")
    }

    private fun removePlayerFromGame(username: String, runningGame: Game): Game {
        runningGame.removePlayer(username)
        return runningGame
    }

}