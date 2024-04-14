package com.example.messageapp.code.ui

/**
 * main activity for the login page
 */
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore.Audio.Media
import android.util.Log
import com.example.messageapp.R
import com.example.messageapp.code.database.DbHelper
import com.example.messageapp.code.logic.Mediator
import com.example.messageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = DbHelper.getInstance(this)
//      This will delete the database for testing purposes each time this view is entered (disable after use)
//        database.deleteAll()

        // This will retrieve status updates about user actions
        val extras = intent.extras
        if (extras != null) {
            val value = extras.getString("status")
            if (value != null) {
                binding.mainUpdates.text = value
            }
        }

        //start the login process if the login button is clicked
        binding.login.setOnClickListener{
            val username : String = binding.username.text.toString()
            val password : String = binding.password.text.toString()

            Mediator.login(this, username, password)
        }

        //start the user creation process
        binding.newUser.setOnClickListener{
            Mediator.newUser(this)
        }

    }
}