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
                val idUser = getLong(getColumnIndexOrThrow(DatabaseContract.GithubUsersColumn.USER_ID))
                users.add(GithubUser(login = username, avatar_url = avatar_url, id = idUser))
            }
        }
        return users
    }
}