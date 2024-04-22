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
import com.example.messageapp.code.ui.adapters.LoginFragment
import com.example.messageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val database = DbHelper.getInstance(this)
        // Comment or remove the line below when not needed
        // database.deleteAll()

        // Load the LoginFragment as the initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, LoginFragment())
                .commit()
        }
    }
}