package com.example.messageapp.code.database.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime

class Message {

    var id : Long = -1
    var text : String = ""
    var senderId: Long = -1
    var receiverId: Long = -1
    @RequiresApi(Build.VERSION_CODES.O)
    var timestamp: String = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()
    var seen : Boolean = false

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(){
         id = -1
         text = ""
         senderId = -1
         receiverId = -1
         timestamp = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()
         seen = false
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(id : Long, text : String, senderId : Long, receiverId: Long, timestamp: String, seen : String){
        this.id = id
        this.text = text
        this.senderId = senderId
        this.receiverId = receiverId
        this.timestamp = timestamp
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(text : String, senderId : Long, receiverId: Long){
        this.text = text
        this.senderId = senderId
        this.receiverId = receiverId
        this.timestamp = LocalTime.now().hour.toString() + ":" + LocalTime.now().minute.toString()
    }
}