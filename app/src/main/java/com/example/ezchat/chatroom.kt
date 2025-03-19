package com.example.ezchat

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlin.collections.hashMapOf

class chatroom : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var reciveruid: String
    private lateinit var chatroomid: String
    private lateinit var adapter: chatrecycleradapter
    private lateinit var recyclerView: RecyclerView
    var messageList = ArrayList<messages>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chatroom)
        var dispusername = findViewById<TextView>(R.id.username)
        var backbtnn = findViewById<ImageView>(R.id.backbtnn)
        var user_message = findViewById<EditText>(R.id.user_send_message)
        var user_message_send_btn = findViewById<ImageView>(R.id.user_message_send)
        recyclerView = findViewById(R.id.chat_recyclerview)
        adapter = chatrecycleradapter(messageList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        db = FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        dispusername.text = intent.getStringExtra("username")
        db.collection("userinfo").whereEqualTo("username", intent.getStringExtra("username")).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    reciveruid = document.id
                    chatroomid = if (FirebaseAuth.getInstance().currentUser?.uid!! < reciveruid) {
                        "${firebaseAuth.currentUser?.uid}_$reciveruid "
                    } else {
                        "${reciveruid}_${firebaseAuth.currentUser?.uid} "
                    }
                    loadMessages()
                }
            }

        backbtnn.setOnClickListener {
            finish()
        }
        user_message_send_btn.setOnClickListener {
            val message = user_message.text.toString()
            val senderuid = FirebaseAuth.getInstance().currentUser?.uid
            val timestamp = Timestamp.now()

            val messageData = HashMap<String, Any>()
            messageData["senderUid"] = senderuid.toString()
            messageData["message"] = message
            messageData["timestamp"] = timestamp


            db.collection("chatroom").document(chatroomid).collection("messages").add(messageData)
                .addOnSuccessListener {
                    Toast.makeText(this, "Message Sent Successfully", Toast.LENGTH_LONG).show()
                    user_message.text.clear()
                    db.collection("chatroom").document(chatroomid).set(
                        mapOf(
                            "senderUid" to senderuid,
                            "receiverUid" to reciveruid
                        ),
                        SetOptions.merge()
                    )
                }

                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Unable to Send the Mesasage Right Now ",
                        Toast.LENGTH_LONG
                    ).show()
                }

        }


    }
    fun loadMessages() {
        db.collection("chatroom").document(chatroomid)
            .collection("messages").orderBy("timestamp")
            .addSnapshotListener { snapshots, _ ->
                if (snapshots != null) {
                    messageList.clear()
                    for (doc in snapshots.documents) {
                        val message = messages(
                            senderuid = doc.getString("senderUid") ?: "",
                            messagetext = doc.getString("message") ?: "",
                            timestamp = doc.getTimestamp("timestamp") ?: Timestamp.now()
                        )
                        messageList.add(message)
                    }
                    adapter.notifyDataSetChanged()
                    recyclerView.scrollToPosition(messageList.size - 1) // Auto-scroll
                }
            }
    }


}
