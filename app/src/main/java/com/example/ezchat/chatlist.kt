package com.example.ezchat

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class chatlist : Fragment() {
    private lateinit var recview: RecyclerView
    private lateinit var chatAdapter: fragmentrecyclerview
    private val userlist = ArrayList<recentchatdata>()
    private val db = FirebaseFirestore.getInstance()
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chatlist, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recview = view.findViewById<RecyclerView>(R.id.fragrecview)
        chatAdapter = fragmentrecyclerview(userlist)
        recview.adapter = chatAdapter
        recview.layoutManager = LinearLayoutManager(requireContext())
        fetchrecentlist()
    }

    private fun fetchrecentlist() {
        userlist.clear()
        val userId = user?.uid ?: return

        db.collection("chatroom").get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    for (document in documents) {
                        val docId = document.id

                        if (docId.contains(userId)) {
                            val senderUid = document.getString("senderUid") ?: ""
                            val receiverUid = document.getString("receiverUid") ?: ""

                            if (senderUid.isNotEmpty() && receiverUid.isNotEmpty()) {
                                val otherUserUid: String
                                if (senderUid == userId) {
                                    otherUserUid = receiverUid
                                } else {
                                    otherUserUid = senderUid
                                }

                                db.collection("userinfo").document(otherUserUid).get()
                                    .addOnSuccessListener { userDoc ->
                                        val username = userDoc.getString("username") ?: "Unknown"
                                        userlist.add(recentchatdata(username))
                                        chatAdapter.notifyDataSetChanged()
                                    }
                            }
                        }
                    }
                }
            }
    }







}