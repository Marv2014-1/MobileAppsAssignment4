package com.example.messageapp.code.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messageapp.R
import com.example.messageapp.code.logic.Mediator
import com.example.messageapp.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginRevert.setOnClickListener(){
            Mediator.back(this)
        }
    }
}