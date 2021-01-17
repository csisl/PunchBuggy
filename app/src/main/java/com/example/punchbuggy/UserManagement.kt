package com.example.punchbuggy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class UserManagement : AppCompatActivity() {

    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        listView = findViewById(R.id.userListView)

        val addUserButton: Button = findViewById(R.id.addUser)
        addUserButton.setOnClickListener {
            val userName: EditText = findViewById(R.id.userNameEditText)
            val name = userName.getText().toString()
            val player = Player(name)
            val toMain = Intent(this, MainActivity::class.java)
            toMain.putExtra("user", name)
            startActivity(toMain)
        }
    }

}