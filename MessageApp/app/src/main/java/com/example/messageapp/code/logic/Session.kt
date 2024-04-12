package com.example.messageapp.code.logic

/**
 * singleton session
 */

import com.example.messageapp.code.database.model.User

object Session {

    lateinit private var user : User

    // set a user as the current user
    fun setUser(user: User){
        this.user = User()
        this.user.id = user.id
        this.user.username = user.username
        this.user.password = user.password
    }

    // return the current user
    fun getUser():User{
        return user
    }

    // log out
    fun endSession(){
        user = User()
    }
}