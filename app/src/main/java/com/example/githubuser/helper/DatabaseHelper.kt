package com.example.githubuser.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.githubuser.contract.DatabaseContract

class DatabaseHelper(applicationContext: Context): SQLiteOpenHelper(applicationContext, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_NAME = "githubUsers"
        private const val DATABASE_VERSION = 1
        private const val SQL_CREATE_TABLE ="CREATE TABLE ${DatabaseContract.GithubUsersColumn.TABLE_NAME} " +
                "(${DatabaseContract.GithubUsersColumn.USERNAME} TEXT PRIMARY KEY, ${DatabaseContract.GithubUsersColumn.AVATAR_URL} TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.GithubUsersColumn.TABLE_NAME}")
        onCreate(db)
    }
}