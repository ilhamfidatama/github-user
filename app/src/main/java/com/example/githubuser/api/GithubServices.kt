package com.example.githubuser.api

import com.example.githubuser.api.response.SearchResponse
import com.example.githubuser.model.GithubUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubServices {
    @GET("search/users")
    fun searchUsers(@Query("q") username: String, @Header("Authorization") auth: String): Call<SearchResponse>

    @GET("users/{username}")
    fun detailUser(@Path("username") username: String, @Header("Authorization") auth: String): Call<GithubUser>

    @GET("users/{username}/followers")
    fun followersUsers(@Path("username") username: String, @Header("Authorization") auth: String): Call<ArrayList<GithubUser>>

    @GET("users/{username}/following")
    fun followingUsers(@Path("username") username: String, @Header("Authorization") auth: String): Call<ArrayList<GithubUser>>
}