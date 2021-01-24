package com.example.punchbuggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.gson.Gson

class UserManagement : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var runningGame: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        listView = findViewById(R.id.userListView)
        var users: MutableList<String> = mutableListOf()

        val gameIntent = getIntent().getStringExtra("runningGame")

        if (gameIntent != null) {
            val gson = Gson()
            runningGame = gson.fromJson(gameIntent, Game::class.java)
            users = runningGame.getPlayerNames()
        } else {
            runningGame = Game()
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        listView.adapter = adapter

        val addUserButton: Button = findViewById(R.id.addUser)
        addUserButton.setOnClickListener {
            val userInput: EditText = findViewById(R.id.userNameEditText)
            val name = userInput.getText().toString()
            users.add(name)
            runningGame = addPlayerToGame(name, runningGame)
            adapter.notifyDataSetChanged()
            userInput.setText("")
        }

        // TODO: update to pass intents when back button is clicked
        val toHomeButton: Button = findViewById(R.id.toHome)
        toHomeButton.setOnClickListener {
            val gson = Gson()
            val toMain = Intent(this, MainActivity::class.java)
            toMain.putExtra("runningGame", gson.toJson(runningGame))
            startActivity(toMain)
        }

    }

    private fun addPlayerToGame(username: String, runningGame: Game): Game {
        val player = Player(username)
        runningGame.addPlayer(player)
        return runningGame
    }

}