package com.example.messageapp.code.database

interface CRUD <T>{
    fun create(t : T) : Long
    fun read(id : Int) : T
    fun update(id : Int, t: T)
    fun delete(id: Int) : Long
}