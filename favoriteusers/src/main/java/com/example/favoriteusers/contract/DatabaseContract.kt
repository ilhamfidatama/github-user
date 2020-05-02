package com.example.favoriteusers.contract

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {
    const val AUTHORITY = "com.example.githubuser"
    const val SCHEME = "content"

    class GithubUsersColumn: BaseColumns{
        companion object{
            const val TABLE_NAME = "github_users"
            const val USERNAME = "username"
            const val AVATAR_URL = "avatar_url"

            val GITHUB_USERS_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}