package com.example.githubuser

import android.database.ContentObserver
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.FavoriteUsersAdapter
import com.example.githubuser.contract.DatabaseContract.GithubUsersColumn.Companion.GITHUB_USERS_URI
import com.example.githubuser.helper.ConverterHelper
import com.example.githubuser.model.GithubUser
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteUsersAdapter

    companion object{
        const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        adapter = FavoriteUsersAdapter(this)
        list_favorite_users.adapter = adapter
        list_favorite_users.setHasFixedSize(true)
        list_favorite_users.layoutManager = LinearLayoutManager(this)
        val handlerT = HandlerThread("favoriteThread")
        handlerT.start()
        val handler = Handler(handlerT.looper)
        val observer = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadUsers()
            }
        }
        contentResolver.registerContentObserver(GITHUB_USERS_URI, true, observer)

        if (savedInstanceState == null){
            loadUsers()
        }else{
            val list = savedInstanceState.getParcelableArrayList<GithubUser>(EXTRA_STATE)
            if (!list.isNullOrEmpty()){
                adapter.addUsers(list)
            }
        }
    }

    private fun loadUsers() {
        GlobalScope.launch(Dispatchers.Main) {
            val db = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(GITHUB_USERS_URI, null, null, null, null)
                ConverterHelper.convertCursorToArrayList(cursor)
            }
            Log.e("favorite_activity", "convert:$db")
            val users = db.await()
            Log.e("favorite_activity", "$users")
            if (users.size > 0) {
                adapter.addUsers(users)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getUsers())
    }
}