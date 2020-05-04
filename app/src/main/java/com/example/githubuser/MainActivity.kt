package com.example.githubuser

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.GithubUsersAdapter
import com.example.githubuser.presenters.ApiPresenter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import com.example.githubuser.contract.DatabaseContract.GithubUsersColumn.Companion.GITHUB_USERS_URI
import com.example.githubuser.helper.ConverterHelper
import com.example.githubuser.model.GithubUser

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GithubUsersAdapter

    companion object{
        const val EXTRA_STATE = "EXTRA_STATE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = GithubUsersAdapter(applicationContext)
        adapter.notifyDataSetChanged()
        github_users_view.adapter = adapter
        github_users_view.setHasFixedSize(true)
        github_users_view.layoutManager = LinearLayoutManager(applicationContext)
        btn_search.setOnClickListener {
            val username = input_search_users.text.toString()
            if (username.isNotEmpty()){
                searchUsers(username)
            }
        }
        input_search_users.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) { }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()){
                    loadFavoriteUser()
                }
            }
        })
        val handlerThread = HandlerThread("favoriteUsers")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val observer = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadFavoriteUser()
            }
        }
        contentResolver.registerContentObserver(GITHUB_USERS_URI, true, observer)
        if (savedInstanceState == null){
            loadFavoriteUser()
        }else{
            val users = savedInstanceState.getParcelableArrayList<GithubUser>(EXTRA_STATE)
            if (!users.isNullOrEmpty()){
                adapter.addUsers(users)
            }
        }
    }

    private fun searchUsers(username: String){
        val apiPresenter = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiPresenter::class.java)
        showLoadingView(true)
        apiPresenter.searchUsers(username)
        apiPresenter.getListUsers().observe(this, Observer {
            adapter.addUsers(it)
            showLoadingView(false)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.language_setting -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.reminder_setting -> startActivity(Intent(this, AppsSettings::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoadingView(showed: Boolean){
        if (showed){
            loading_view.visibility = View.VISIBLE
            github_users_view.visibility = View.GONE
        }else{
            loading_view.visibility = View.GONE
            github_users_view.visibility = View.VISIBLE
        }
    }

    private fun loadFavoriteUser(){
        GlobalScope.launch(Dispatchers.Main){
            val db = async(Dispatchers.IO){
                val cursor = contentResolver.query(GITHUB_USERS_URI, null, null, null, null)
                ConverterHelper.convertCursorToArrayList(cursor)
            }
            val users = db.await()
            if (users.size > 0){
                adapter.addUsers(users)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.getUsers())
    }
}
