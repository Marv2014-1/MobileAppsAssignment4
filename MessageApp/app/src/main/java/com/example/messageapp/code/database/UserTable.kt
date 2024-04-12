package com.example.messageapp.code.database

/**
 * handles access to the user table
 */

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.util.Log
import com.example.messageapp.code.database.model.User

class UserTable (private val context: Context) : CRUD <User> {

    //user table variables
    private val USER_TABLE_NAME = "user"
    private val USER_COLUMN_ID = "id"
    private val USER_COLUMN_USERNAME = "username"
    private val USER_COLUMN_PASSWORD = "password"

    // create a user given a user with a username and password
    override fun create(user: User): Long {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(USER_COLUMN_USERNAME, user.username)
                content.put(USER_COLUMN_PASSWORD, user.password)

                id = db.insert(USER_TABLE_NAME, null, content)

                user.id = id
            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    // get a user given an id
    @SuppressLint("Range")
    override fun read(id: Long): User {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_ID = \"$id\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var user : User? = null

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    val id = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_ID))
                    val username = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME))
                    val password = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD))

                    user = User(username, password)
                    user.id = id
                } while (cursor.moveToNext())
            }
        }

        database.close()
        cursor.close()
        if (user != null){
            return user
        }
        return User()
    }

    //get a user given a username and password
    @SuppressLint("Range")
    fun readByUsernamePassword(username: String, password: String): User? {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_USERNAME = \"$username\"" +
                " AND $USER_COLUMN_PASSWORD = \"$password\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var user : User? = null

        if (cursor != null){
            if (cursor.moveToFirst()){
                val id = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_ID))
                val username = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME))
                val password = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD))

                user = User(id, username, password)
            }
        }

        database.close()
        cursor.close()
        return user
    }

    //Get all users except the current user logged in
    @SuppressLint("Range")
    fun readALLOthers(userId: Long): ArrayList<User> {
        val database= DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $USER_TABLE_NAME WHERE $USER_COLUMN_ID != $userId"
        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        val users = ArrayList<User>()
        if (cursor != null){
            if (cursor.moveToFirst()){
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(USER_COLUMN_ID))
                    val username = cursor.getString(cursor.getColumnIndex(USER_COLUMN_USERNAME))
                    val password = cursor.getString(cursor.getColumnIndex(USER_COLUMN_PASSWORD))

                    var user = User(id, username, password)
                    users.add(user)
                    Log.e("UserTable", user.username + " AND " + user.id.toString())
                } while (cursor.moveToNext())
            }
        }

        database.close()
        cursor.close()
        return users
    }

    // delete a user given an id
    override fun delete(id: Long): Long {
        val database= DbHelper.getInstance(context)
        TODO("Not yet implemented")
    }

    // update a user given an id and a user
    override fun update(id: Long, user: User) {
        val database = DbHelper.getInstance(context)
        val db : SQLiteDatabase = database.writableDatabase
        val updateQuery = "UPDATE $USER_TABLE_NAME SET $USER_COLUMN_PASSWORD = \"${user.password}\" AND $USER_COLUMN_USERNAME = \"${user.username}\"" +
                " WHERE $USER_COLUMN_ID = \"$id\" "

        db.execSQL(updateQuery)
        database.close()
    }
}