package com.example.messageapp.code.database.model

class User (){
    // Every user will need an ID, username which they pick, and password they pick
    var id : Long = -1
    var username : String = ""
    var password : String = ""

    constructor(username : String, password : String) : this() {
        this.username = username
        this.password = password
    } // Constructor for new user with a username and password

    constructor(id : Long,username : String, password : String) : this() {
        this.id = id
        this.username = username
        this.password = password
    } // constructor with username, password, and ID.
}