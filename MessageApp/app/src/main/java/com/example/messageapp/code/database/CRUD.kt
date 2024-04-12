package com.example.messageapp.code.database

/**
 * Crud interface
 */

interface CRUD <T>{
    fun create(t : T) : Long
    fun read(id : Long) : T
    fun update(id : Long, t: T)
    fun delete(id: Long) : Long
}