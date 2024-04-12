package com.example.messageapp.code.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messageapp.R
import com.example.messageapp.code.database.model.User

class UserAdapter(private val context: Context, private val peopleList: ArrayList<User>, private val listener: OnButtonClickListener) : RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    //Define button click functionality
    interface OnButtonClickListener{
        fun onButtonClick(position: Int, buttonId: Int)
    }

    //Define items as they appear in the context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user, parent, false )
        return MyViewHolder(itemView, listener)
    }

    //Define how many items there are
    override fun getItemCount(): Int {
        return peopleList.size
    }

    //Set updated text text
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = peopleList[position]
        holder.username.text = currentItem.username
    }

    //Bind items to named variables for access
    class MyViewHolder(itemView : View, listener: OnButtonClickListener) : RecyclerView.ViewHolder(itemView){
        val username : TextView = itemView.findViewById(R.id.userUsername)

        val messageButton : Button = itemView.findViewById(R.id.messageUser)

        // set the button id to the current position
        init {
            messageButton.setOnClickListener() {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onButtonClick(adapterPosition, R.id.messageUser)
                }
            }
        }
    }
}