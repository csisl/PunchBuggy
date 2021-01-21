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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_management)
        listView = findViewById(R.id.userListView)

        // display all of the users created
        val users: MutableList<String> = ArrayList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, users)
        listView.adapter = adapter

        val addUserButton: Button = findViewById(R.id.addUser)
        addUserButton.setOnClickListener {
            val userName: EditText = findViewById(R.id.userNameEditText)
            val name = userName.getText().toString()
            users.add(name)
            adapter.notifyDataSetChanged()
            // TODO: do we really need to make a player object here? can we
            // just pass a list of strings for all of the names added?
            // or maybe we should receive the Game object from the intent and add to it here
            val player = Player(name)
            val gson = Gson()
            val toMain = Intent(this, MainActivity::class.java)
            toMain.putExtra("player", gson.toJson(player))
            startActivity(toMain)
        }
    }

}