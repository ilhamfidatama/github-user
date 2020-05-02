package com.example.favoriteusers.helper

import android.database.Cursor
import com.example.favoriteusers.contract.DatabaseContract
import com.example.favoriteusers.model.GithubUser

object ConverterHelper {
    fun convertCursorToArrayList(cursor: Cursor?): ArrayList<GithubUser>{
        val users = arrayListOf<GithubUser>()
        cursor?.apply {
            while (moveToNext()){
                val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.USERNAME))
                val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.AVATAR_URL))
                users.add(GithubUser(login = username, avatar_url = avatar_url))
            }
        }
        return users
    }

    fun convertCursorToObject(cursor: Cursor?): GithubUser {
        var user = GithubUser()
        cursor?.apply {
            moveToFirst()
            val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.USERNAME))
            val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.AVATAR_URL))
            user = GithubUser(login = username, avatar_url = avatar_url)
        }
        return user
    }
}