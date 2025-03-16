package com.example.ezchat

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class searchuser : AppCompatActivity() {
    private lateinit var adapter:myAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var user: FirebaseAuth
var userinfo = ArrayList<userinfo>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_searchuser)
        userinfo = arrayListOf()
        var search_user= findViewById<ImageView>(R.id.search_user_btn)
        var recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        adapter = myAdapter(userinfo)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(this)

        search_user.setOnClickListener{
            var query_mail = findViewById<EditText>(R.id.query_mail)
            fetchdatafromdb(query_mail.text.toString())
        }
    }


    private fun fetchdatafromdb(query_mail: String) {
        user = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        if (user.currentUser != null) {
            db.collection("userinfo")
                .whereEqualTo("usermail", query_mail)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        userinfo.clear()
                        for (document in documents) {
                            val name = document.getString("username") ?: "No Name"
                            val email = document.getString("usermail") ?: "No Email"

                            // Add user to the list
                            userinfo.add(userinfo(name, email))
                        }

                        adapter.notifyDataSetChanged() // Update RecyclerView
                    } else {
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(this, "Error: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}