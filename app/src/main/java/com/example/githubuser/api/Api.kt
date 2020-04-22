package com.example.githubuser.api

import com.example.githubuser.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Api {
    private val BASE_URL = BuildConfig.BASE_URL

    fun create(): GithubServices{
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
        return retrofit.create(GithubServices::class.java)
    }
}