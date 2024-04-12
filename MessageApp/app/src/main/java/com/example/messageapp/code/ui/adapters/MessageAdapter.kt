package com.example.messageapp.code.ui.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.messageapp.R
import com.example.messageapp.code.logic.Mediator

class MessageAdapter (private val context: Context, private val messageList: ArrayList<com.example.messageapp.code.database.model.Message>) : RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {

    //Define button click functionality

    //Define items as they appear in the context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.message, parent, false )
        return MyViewHolder(itemView)
    }

    //Define how many items there are
    override fun getItemCount(): Int {
        return messageList.size
    }

    //Set updated text text
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem : com.example.messageapp.code.database.model.Message = messageList[position]
        Log.e("Message Adapter Sender", currentItem.senderId.toString())
        Log.e("Message Adapter Receiver", currentItem.receiverId.toString())
        holder.username.text = Mediator.getUserName(context,currentItem.receiverId)
        holder.time.text = currentItem.timestamp
        holder.text.text = currentItem.text
    }

    //Bind items to named variables for access
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val username : TextView = itemView.findViewById(R.id.messageSender)
        val time : TextView = itemView.findViewById(R.id.messageTime)
        val text : TextView = itemView.findViewById(R.id.messageText)
    }
}