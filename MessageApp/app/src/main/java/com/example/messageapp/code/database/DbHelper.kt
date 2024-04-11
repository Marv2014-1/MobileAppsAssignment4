package com.example.messageapp.code.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper private constructor(private var context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, 1) {

    companion object {
        private var database: DbHelper? = null

        private val DATABASE_NAME = "messaging_database"

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            if (database == null) {
                database = DbHelper(context)
            }
            return database as DbHelper
        }
    }

    //message table variables
    private val MESSAGE_TABLE_NAME = "messages"
    private val MESSAGE_COLUMN_ID = "id"
    private val MESSAGE_COLUMN_SENDER = "sender"
    private val MESSAGE_COLUMN_RECEIVER = "receiver"
    private val MESSAGE_COLUMN_TEXT = "text"
    private val MESSAGE_COLUMN_TIME = "time"
    private val MESSAGE_COLUMN_SEEN = "seen"

    //user table variables
    private val USER_TABLE_NAME = "user"
    private val USER_COLUMN_ID = "id"
    private val USER_COLUMN_USERNAME = "username"
    private val USER_COLUMN_PASSWORD = "username"

    // Create the user table
    private val SQL_CREATE_USER_TABLE: String
        get() = """
                CREATE TABLE IF NOT EXISTS "$USER_TABLE_NAME" (
                    "$USER_COLUMN_ID" INTEGER NOT NULL UNIQUE,
                    "$USER_COLUMN_USERNAME" TEXT NOT NULL UNIQUE,
                    "$USER_COLUMN_PASSWORD" TEXT NOT NULL,
                    PRIMARY KEY("$USER_COLUMN_ID" AUTOINCREMENT)
                )
            """

    private val SQL_CREATE_MESSAGE_TABLE: String
        get() = """
                    CREATE TABLE IF NOT EXISTS "$MESSAGE_TABLE_NAME" (
                    "$MESSAGE_COLUMN_ID"	INTEGER NOT NULL UNIQUE,
                    "$MESSAGE_COLUMN_SENDER"	INTEGER NOT NULL,
                    "$MESSAGE_COLUMN_RECEIVER"	INTEGER NOT NULL,
                    "$MESSAGE_COLUMN_TEXT"	TEXT NOT NULL,
                    "$MESSAGE_COLUMN_TIME"	TEXT NOT NULL,
                    "$MESSAGE_COLUMN_SEEN" TEXT,
                    FOREIGN KEY("$MESSAGE_COLUMN_RECEIVER") REFERENCES "user"("$USER_COLUMN_ID"),
                    FOREIGN KEY("$MESSAGE_COLUMN_RECEIVER") REFERENCES "user"("$USER_COLUMN_ID"),
                    PRIMARY KEY("$MESSAGE_COLUMN_ID" AUTOINCREMENT)
                )
            """

    /**
     * executed when the database is created, this does not mean each time the program is ran since
     * the database is saved within the emulator across different executions
     */
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_USER_TABLE)
        db?.execSQL(SQL_CREATE_MESSAGE_TABLE)
    }

    // don't worry about this
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        deleteAll()

        if (p0 != null) {
            onCreate(p0)
        }
    }

    //drops all the tables from the database
    fun deleteAll(){
        this.writableDatabase.execSQL("DROP TABLE IF EXISTS user")
        this.writableDatabase.execSQL("DROP TABLE IF EXISTS messages")
    }
}