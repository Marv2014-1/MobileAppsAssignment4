package com.example.messageapp.code.ui

/**
 * this class allows for users to send and recive messages
 */

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messageapp.code.database.model.Message
import com.example.messageapp.code.logic.Mediator
import com.example.messageapp.code.ui.adapters.MessageAdapter
import com.example.messageapp.databinding.ActivityMessageBinding

class MessageList : AppCompatActivity(){

    companion object{
        var target : Long = -1
    }

    private lateinit var binding: ActivityMessageBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<Message>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // set up message list
        setUpAdapter(this)

        // return to menu
        binding.messagesBack.setOnClickListener(){
            Mediator.back(this)
        }

        // send a message with the entered text
        binding.sendMessage.setOnClickListener(){
            val message = binding.messageText.text.toString()
            Mediator.sendMessage(this, target, message)
        }
    }

    // This adapter is adapts the message.xml to the recycler view
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setUpAdapter(context: Context){
        // get the messages relating to the current conversation
        newArrayList = Mediator.getMessages(context, target)

        // sort the messages based on time sent
        newArrayList.sortWith(TimeComparator())

        // create the recyclerview
        newRecyclerView = binding.messageList
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        //populate the recyclerview
        var adapter = MessageAdapter(context, newArrayList)
        newRecyclerView.adapter = adapter
    }

    //this is a comparator to list the messages in order
    class TimeComparator : Comparator<Message> {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun compare(time1: Message, time2: Message): Int {
            val (hour1, minute1) = time1.timestamp.split(":").map { it.toInt() }
            val (hour2, minute2) = time2.timestamp.split(":").map { it.toInt() }

            if (hour1 != hour2) {
                return hour1.compareTo(hour2)
            } else {
                return minute1.compareTo(minute2)
            }
        }
    }
}