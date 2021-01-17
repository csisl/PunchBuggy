package com.example.punchbuggy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var playerListView: ListView
    private lateinit var playerViewAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // A game exists, so load the users into a list view
        if (savedInstanceState != null) {
            TODO()
        }

        val runningGame = Game()

        val userObj = getIntent().getStringExtra("player")
        if (userObj != null) {
            val gson = Gson()
            val player = gson.fromJson(userObj, Player::class.java)
            runningGame.addPlayer(player)
        }

        playerListView = findViewById(R.id.playerListView)
        playerViewAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, runningGame.getPlayerNames())
        playerListView.adapter = playerViewAdapter

        val toUserManagement: Button = findViewById(R.id.userManagment)
        toUserManagement.setOnClickListener {
            val toUserManagementView = Intent(this, UserManagement::class.java)
            startActivity(toUserManagementView)
        }

        val toUser: Button = findViewById(R.id.viewGames)
        toUser.setOnClickListener {
            val toUserView = Intent(this, UserView::class.java)
            startActivity(toUserView)
        }
    }
}