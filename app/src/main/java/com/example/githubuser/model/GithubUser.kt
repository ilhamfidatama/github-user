package com.example.githubuser.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GithubUser(
    var id: Long,
    var login: String,
    var name: String?,
    var avatar_url: String,
    var company: String?,
    var location: String?,
    var followers_url: String,
    var following_url: String,
    var repos_url: String,
    var followers: Int?,
    var following: Int?
): Parcelable