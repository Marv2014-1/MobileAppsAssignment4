package com.example.messageapp.code.logic

/**
 * this class handles logic for between the UI
 */

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.messageapp.code.database.MessageTable
import com.example.messageapp.code.database.UserTable
import com.example.messageapp.code.database.model.Message
import com.example.messageapp.code.database.model.User
import com.example.messageapp.code.ui.Login
import com.example.messageapp.code.ui.MainActivity
import com.example.messageapp.code.ui.MessageList
import com.example.messageapp.code.ui.NewUser

object Mediator  {

    private var previousState = ""

    // Takes a username and password and ensures that the user exists in the database
    // If a user does exist, we start the session and change the view
    fun login(context: Context, username: String, password: String){
        val userTable = UserTable(context)
        val user : User? = userTable.readByUsernamePassword(username, password)

        if (user == null){
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("status", "Incorrect entry or user does not exist")
            context.startActivity(intent)
            return
        }

        Session.setUser(user)
        loginView(context)
    }

    // take the user to the log in menu
    fun loginView(context: Context){
        val intent : Intent = Intent(context, Login::class.java)
        previousState = "main"
        context.startActivity(intent)
    }

    // get the username of the given id
    fun getUserName(context: Context ,id : Long) : String{
        var userTable = UserTable(context)
        return userTable.read(id).username.toString()
    }

    // return a list of all users except the one currently logged in
    fun getAllOtherUsers(context: Context) : ArrayList<User>{
        val user = Session.getUser()
        val db = UserTable(context)
        val userArray = db.readALLOthers(user.id)
        for (users in userArray){
            Log.e("user", users.id.toString())
        }
        return userArray
    }

    // change the view to the new user view
    fun newUser(context: Context){
        val intent = Intent(context, NewUser::class.java)
        previousState = "main"
        context.startActivity(intent)
    }

    // create a new user given a username and password
    fun createNewUser(context: Context, username :String, password : String){

        val user : User = User(username, password)
        val userTable = UserTable(context)
        val code = userTable.create(user)

        var update : String = ""

        if (code.toInt() == -1) {
            update = "Failed to create a user"
        } else {
            update = "User account added to the database"
        }

        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra("status", update)
        context.startActivity(intent)
    }

    // change the view to the message view
    fun message(context: Context , target: Long){
        val intent = Intent(context, MessageList::class.java)
        MessageList.target = target
        previousState = "menu"
        context.startActivity(intent)
    }

    // send a message given the receiver and text content
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendMessage(context: Context, target: Long, text : String){
        val messageTable = MessageTable(context)
        val user = Session.getUser()
        val message = Message(text, target, user.id)
        messageTable.create(message)
        message(context, target)
    }

    // get the messages between the current conversation
    @RequiresApi(Build.VERSION_CODES.O)
    fun getMessages(context: Context, targetId: Long): ArrayList<Message> {
        val messageTable = MessageTable(context)
        val user = Session.getUser()

        val messageArray = messageTable.readCurrentConvo(user.id, targetId)
        return  messageArray
    }

    // go back one view
    fun back(context : Context){
        if (previousState == "main"){
            val intent = Intent(context, MainActivity::class.java)
            Session.endSession()
            context.startActivity(intent)
        } else if(previousState == "menu"){
            loginView(context)
        }
    }
}