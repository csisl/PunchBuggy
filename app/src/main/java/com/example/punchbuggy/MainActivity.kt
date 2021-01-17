package com.example.punchbuggy

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // A game exists, so load the users into a list view
        if (savedInstanceState != null) {
            TODO()
        }

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