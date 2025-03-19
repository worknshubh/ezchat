package com.example.ezchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class fragmentrecyclerview(var recentchat:ArrayList<recentchatdata>): RecyclerView.Adapter<fragmentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): fragmentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recentchatlist,parent,false)
        return fragmentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recentchat.size
    }

    override fun onBindViewHolder(holder: fragmentViewHolder, position: Int) {
        var recent = recentchat[position]
        holder.username.text = recent.username
        holder.recentmessage.text = recent.lastmessage
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, chatroom::class.java)
            intent.putExtra("username", recent.username) // Pass username to chatroom
            holder.itemView.context.startActivity(intent)
        }
    }
}
class fragmentViewHolder(View: View):RecyclerView.ViewHolder(View){
    var username = View.findViewById<TextView>(R.id.recent_user_name)
    var recentmessage = View.findViewById<TextView>(R.id.recent_message)

}