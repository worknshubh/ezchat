package com.example.ezchat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class chatrecycleradapter(var messagelist:ArrayList<messages>): RecyclerView.Adapter<chatViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): chatViewHolder {
        var inflater = LayoutInflater.from(parent.context)
        var view = inflater.inflate(R.layout.chat_messages_view,parent,false)
        return chatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return messagelist.size
    }

    override fun onBindViewHolder(holder: chatViewHolder, position: Int) {
        var message = messagelist[position]
        if(message.senderuid == FirebaseAuth.getInstance().currentUser?.uid.toString()){
            holder.leftchatlayout.visibility = View.GONE
            holder.rightchatlayout.visibility = View.VISIBLE
            holder.rightchatmessage.text = message.messagetext
        }
        else{
            holder.leftchatlayout.visibility = View.VISIBLE
            holder.rightchatlayout.visibility = View.GONE
            holder.leftchatmessage.text = message.messagetext
        }
    }
}
class chatViewHolder(view: View):RecyclerView.ViewHolder(view){
    var leftchatlayout = view.findViewById<LinearLayout>(R.id.left_side_message_view)
    var leftchatmessage = view.findViewById<TextView>(R.id.text_received)
    var rightchatlayout= view.findViewById<LinearLayout>(R.id.right_side_message_view)
    var rightchatmessage = view.findViewById<TextView>(R.id.text_sent)
}