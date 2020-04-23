package com.example.githubuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.adapter.GithubUsersAdapter
import com.example.githubuser.presenters.ApiPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: GithubUsersAdapter

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
}
