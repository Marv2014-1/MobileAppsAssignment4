package com.example.messageapp.code.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.messageapp.R
import com.example.messageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)

    }
}