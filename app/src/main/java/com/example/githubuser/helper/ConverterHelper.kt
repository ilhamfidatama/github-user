package com.example.githubuser.helper

import android.database.Cursor
import com.example.githubuser.contract.DatabaseContract
import com.example.githubuser.model.GithubUser

object ConverterHelper {
    fun convertCursorToArrayList(cursor: Cursor?): ArrayList<GithubUser>{
        val users = arrayListOf<GithubUser>()
        cursor?.apply {
            while (moveToNext()){
                val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.USERNAME))
                val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.AVATAR_URL))
                val id = getLong(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.USER_ID))
                users.add(GithubUser(login = username, avatar_url = avatar_url, id = id))
            }
        }
        return users
    }

    fun convertCursorToObject(cursor: Cursor?): GithubUser{
        var user = GithubUser()
        cursor?.apply {
            moveToFirst()
            val username = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.USERNAME))
            val avatar_url = getString(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.AVATAR_URL))
            val id = getLong(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.USER_ID))
            user = GithubUser(login = username, avatar_url = avatar_url, id = id)
        }
        return user
    }
}