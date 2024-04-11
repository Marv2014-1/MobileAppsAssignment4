package com.example.messageapp.code.database.model

class User (){
    var id : Long = -1
    var username : String = ""
    var password : String = ""

    constructor(username : String, password : String) : this() {
        this.username = username
        this.password = password
    }
}