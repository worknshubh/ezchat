package com.example.ezchat

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        var chatlistbtn = findViewById<ImageView>(R.id.imageView4)
        var profileicon = findViewById<ImageView>(R.id.imageView5)
        var searchbtn = findViewById<ImageView>(R.id.searchicon_btn)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentcont,chatlist())
            .commit()

        profileicon.setOnClickListener{
            replacefrag(profilefrag())
        }
        chatlistbtn.setOnClickListener{
            replacefrag(chatlist())
        }
        searchbtn.setOnClickListener{
            startActivity(Intent(this,searchuser::class.java))
        }
    }

    fun replacefrag(newfrag:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentcont,newfrag)
            .commit()
    }
}