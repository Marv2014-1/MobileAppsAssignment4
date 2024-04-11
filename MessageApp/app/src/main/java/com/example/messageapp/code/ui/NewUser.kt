package com.example.messageapp.code.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messageapp.R
import com.example.messageapp.code.logic.Mediator
import com.example.messageapp.databinding.ActivityNewUserBinding

class NewUser : AppCompatActivity() {
    lateinit var binding: ActivityNewUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.submitNewUser.setOnClickListener(){
            val username : String = binding.newUserUsername.text.toString()
            val password : String = binding.newUserPassword.text.toString()
            Mediator.createNewUser(this, username, password)
        }

        binding.backNewUser.setOnClickListener(){
            Mediator.back(this)
        }
    }
}