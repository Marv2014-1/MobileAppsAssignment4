package com.example.messageapp.code.ui

/**
 * This class will appear after a user has logged in
 */

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messageapp.R
import com.example.messageapp.code.database.model.User
import com.example.messageapp.code.logic.Mediator
import com.example.messageapp.code.ui.adapters.UserAdapter
import com.example.messageapp.databinding.FragmentUserScreenBinding

class Login : AppCompatActivity(), UserAdapter.OnButtonClickListener {
    lateinit var binding: FragmentUserScreenBinding
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentUserScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // return to main (log out)
        binding.loginRevert.setOnClickListener(){
            Mediator.back(this)
        }

        // set up user list
        setUpAdapter(this)
    }

    // This adapter is adapts the user.xml to the recycler view
    private fun setUpAdapter(context : Context){
        // get all the users except the one currently logged in
        newArrayList = Mediator.getAllOtherUsers(this)

        // bind the recyclerview
        newRecyclerView = binding.userList
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.setHasFixedSize(true)

        // populate the adaptor
        var adapter = UserAdapter(context ,newArrayList, this)
        newRecyclerView.adapter = adapter
    }

    // Once a message button is clicked, it will trigger the MessageList view
    override fun onButtonClick(position: Int, buttonId: Int) {
        if (R.id.messageUser == buttonId){
            Log.e("username", newArrayList.get(position).username)
            val personId = newArrayList.get(position).id
            Mediator.message(this, personId)
        }
    }
}