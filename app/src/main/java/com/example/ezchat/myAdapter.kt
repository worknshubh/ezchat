package com.example.ezchat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myAdapter(val Context:Context,var userinfo: List<userinfo>): RecyclerView.Adapter<myViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.myitems,parent,false)
        return myViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userinfo.size
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        val user = userinfo[position]
       holder.user_name.text = user.username
        holder.user_mail.text = user.usermail
        holder.itemView.setOnClickListener{
        val intent = Intent(Context,chatroom::class.java)
            intent.putExtra("username",holder.user_name.text)
            Context.startActivity(intent)


        }
    }
}
class myViewHolder(View:View):RecyclerView.ViewHolder(View){
    var user_name = View.findViewById<TextView>(R.id.user_name)
    var user_mail = View.findViewById<TextView>(R.id.user_mail)
}