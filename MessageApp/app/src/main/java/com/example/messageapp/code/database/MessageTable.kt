package com.example.messageapp.code.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.messageapp.code.database.model.Message
import com.example.messageapp.code.database.model.User

class MessageTable (private var context: Context) : CRUD<Message> {

    //message table variables
    private val MESSAGE_TABLE_NAME = "messages"
    private val MESSAGE_COLUMN_ID = "id"
    private val MESSAGE_COLUMN_SENDER = "sender"
    private val MESSAGE_COLUMN_RECEIVER = "receiver"
    private val MESSAGE_COLUMN_TEXT = "text"
    private val MESSAGE_COLUMN_TIME = "time"
    private val MESSAGE_COLUMN_SEEN = "seen"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun create(message: Message): Long {
        var id: Long = -1
        val database= DbHelper.getInstance(context)

        try {
            database.writableDatabase.use { db ->
                val content = ContentValues()

                content.put(MESSAGE_COLUMN_SENDER, message.senderId)
                content.put(MESSAGE_COLUMN_RECEIVER, message.receiverId)
                content.put(MESSAGE_COLUMN_TEXT, message.text)
                content.put(MESSAGE_COLUMN_TIME, message.timestamp)
                content.put(MESSAGE_COLUMN_SEEN, message.seen.toString())

                id = db.insert(MESSAGE_TABLE_NAME, null, content)

                message.id = id
            }
        } catch (e : SQLiteException){
            println(e)
        }

        database.close()
        return id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    override fun read(id: Int): Message {
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $MESSAGE_TABLE_NAME WHERE $MESSAGE_COLUMN_ID = \"$id\""

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        var message : Message? = null

        if (cursor != null){
            if (cursor.moveToFirst()){
                do{
                    val id = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_ID))
                    val sender = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_SENDER))
                    val receiver = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_RECEIVER))
                    val text = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TEXT))
                    val time = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TIME))
                    val seen = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_SEEN))

                    message = Message(id, text, sender, receiver, time, seen)
                } while (cursor.moveToNext())
            }
        }

        database.close()
        cursor.close()
        if (message != null){
            return message
        }
        return Message()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("Range")
    fun readCurrentConvo(userId: Long, targetId: Long) : ArrayList<Message>{
        val database = DbHelper.getInstance(context)
        val selectQuery = "SELECT * FROM $MESSAGE_TABLE_NAME WHERE " +
                " ($MESSAGE_COLUMN_SENDER = \"$userId\" AND $MESSAGE_COLUMN_RECEIVER = \"$targetId\") " +
                " OR ($MESSAGE_COLUMN_SENDER = \"$targetId\" AND $MESSAGE_COLUMN_RECEIVER = \"$userId\") " +
                "ORDER BY $MESSAGE_COLUMN_TIME DESC"

        val db : SQLiteDatabase = database.writableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        var messages : ArrayList<Message> = ArrayList()

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_ID))
                    val sender = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_SENDER))
                    val receiver = cursor.getLong(cursor.getColumnIndex(MESSAGE_COLUMN_RECEIVER))
                    val text = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TEXT))
                    val time = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_TIME))
                    val seen = cursor.getString(cursor.getColumnIndex(MESSAGE_COLUMN_SEEN))

                    val message = Message(id, text, sender, receiver, time, seen)

                    messages.add(message)


                }while (cursor.moveToNext())
            }
        }

        cursor.close()
        database.close()
        return messages
    }

    override fun delete(id: Int): Long {
        TODO("Not yet implemented")
    }

    override fun update(id: Int, t: Message) {
        TODO("Not yet implemented")
    }
}