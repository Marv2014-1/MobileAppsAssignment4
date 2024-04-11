package com.example.messageapp.code.logic

import com.example.messageapp.code.database.model.User

object Session {

    lateinit private var user : User

    fun setUser(user: User){
        this.user = User()
        this.user.id = user.id
        this.user.username = user.username
        this.user.password = user.password
    }
}