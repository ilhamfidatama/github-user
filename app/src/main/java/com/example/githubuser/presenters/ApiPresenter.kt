package com.example.githubuser.presenters

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.BuildConfig
import com.example.githubuser.api.Api
import com.example.githubuser.api.response.SearchResponse
import com.example.githubuser.model.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiPresenter: ViewModel() {
    private val TOKEN = BuildConfig.API_KEY
    private val api = Api.create()
    private var listUsers = MutableLiveData<ArrayList<GithubUser>>()
    private var usersLoaded = MutableLiveData<Boolean>()
    private var detailUser = MutableLiveData<GithubUser>()

    fun searchUsers(username: String){
        api.searchUsers(username, TOKEN).enqueue(object : Callback<SearchResponse>{
            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                Log.e("search-request", t.message.toString())
            }

            override fun onResponse(
                call: Call<SearchResponse>,
                response: Response<SearchResponse>
            ) {
                if (response.isSuccessful){
                    proccessUsers(response.body())
                }
            }
        })
    }

    fun followersUsers(username: String){
        api.followersUsers(username, TOKEN).enqueue(object : Callback<ArrayList<GithubUser>>{
            override fun onFailure(call: Call<ArrayList<GithubUser>>, t: Throwable) {
                Log.e("followers-request", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GithubUser>>,
                response: Response<ArrayList<GithubUser>>
            ) {
                proccessFollowUsers(response.body())
            }
        })
    }

    fun followingUsers(username: String){
        api.followingUsers(username, TOKEN).enqueue(object : Callback<ArrayList<GithubUser>>{
            override fun onFailure(call: Call<ArrayList<GithubUser>>, t: Throwable) {
                Log.e("following-request", t.message.toString())
            }

            override fun onResponse(
                call: Call<ArrayList<GithubUser>>,
                response: Response<ArrayList<GithubUser>>
            ) {
                proccessFollowUsers(response.body())
            }
        })
    }

    fun detailUsers(username: String){
        api.detailUser(username, TOKEN).enqueue(object : Callback<GithubUser>{
            override fun onFailure(call: Call<GithubUser>, t: Throwable) {
                Log.e("detail-request", t.message.toString())
            }

            override fun onResponse(call: Call<GithubUser>, response: Response<GithubUser>) {
                response.body()?.let {
                    detailUser.postValue(it)
                }
            }

        })
    }

    private fun proccessUsers(users: SearchResponse?){
        users?.let {
            listUsers.postValue(it.items)
        }
    }

    private fun proccessFollowUsers(users: ArrayList<GithubUser>?){
        users?.let {
            listUsers.postValue(it)
            usersLoaded.postValue(true)
        }
    }

    fun getListUsers(): LiveData<ArrayList<GithubUser>> = listUsers

    fun getDetailUser(): LiveData<GithubUser> = detailUser

    fun usersIsLoaded(): LiveData<Boolean> = usersLoaded
}