package com.example.ezchat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class chatroom : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatroom)
        var dispusername = findViewById<TextView>(R.id.username)
        var backbtnn = findViewById<ImageView>(R.id.backbtnn)
        dispusername.text = intent.getStringExtra("username")
        backbtnn.setOnClickListener{
            startActivity(Intent(this,searchuser::class.java))
            finish()
        }
    }
}