package com.example.githubuser.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.githubuser.contract.DatabaseContract.GithubUsersColumn.Companion.TABLE_NAME
import com.example.githubuser.contract.DatabaseContract.GithubUsersColumn.Companion.USER_ID

class GithubUsersHelper(applicationContext: Context) {
    private val dbHelper = DatabaseHelper(applicationContext)
    private lateinit var db: SQLiteDatabase

    companion object{
        private const val DB_TABLE = TABLE_NAME
        private var INSTANCE: GithubUsersHelper? = null

        fun getInstance(context: Context): GithubUsersHelper =
                INSTANCE ?: synchronized(this){
                    INSTANCE ?: GithubUsersHelper(context)
                }
    }

    fun open(){
        db = dbHelper.writableDatabase
    }

    fun queryAll(): Cursor = db.query(DB_TABLE, null, null, null, null, null, null, null)

    fun queryByUsername(userID: String): Cursor = db.query(DB_TABLE, null, "$USER_ID = ?", arrayOf(userID), null, null, null)

    fun save(values: ContentValues?): Long = db.insert(DB_TABLE, null, values)

    fun deleteByUsersname(userID: String): Int = db.delete(DB_TABLE, "$USER_ID = $userID", null)
}