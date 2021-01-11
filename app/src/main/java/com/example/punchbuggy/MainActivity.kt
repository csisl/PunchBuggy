package com.example.punchbuggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toUser: Button = findViewById(R.id.viewGames)
        toUser.setOnClickListener {
            val toUserView = Intent(this, UserView::class.java)
            startActivity(toUserView)
        }
    }
}