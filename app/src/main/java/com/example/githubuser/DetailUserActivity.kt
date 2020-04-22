package com.example.githubuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.adapter.MenuFollowAdapter
import com.example.githubuser.model.GithubUser
import com.example.githubuser.presenters.ApiPresenter
import kotlinx.android.synthetic.main.activity_detail_user.*

class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val USER_PROFILE = "USER PROFILE"
    }
    private lateinit var menu: Array<String>

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        val userProfile = intent.getParcelableExtra(USER_PROFILE) as GithubUser
        loadDetailUser(userProfile.login)
        menu = resources.getStringArray(R.array.menu_follow)
        Log.d("menu", "$menu")
        val menuAdapter = MenuFollowAdapter(menu, userProfile.login, supportFragmentManager)
        follow_content.adapter = menuAdapter
        menu_follow.setupWithViewPager(follow_content)
    }

    @SuppressLint("SetTextI18n")
    private fun loadDetailUser(username: String){
        val apiPresenter = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiPresenter::class.java)
        apiPresenter.detailUsers(username)
        apiPresenter.getDetailUser().observe(this, Observer {
            name_of_user.text = it.name
            company_of_user.text = "${company_of_user.text} ${it.company}"
            total_followers.text = "${total_followers.text} ${it.followers}"
            total_following.text = "${total_following.text} ${it.following}"
            val glide = Utils.loadImageProfile(this, it.avatar_url)
            glide.into(avatar_user)
        })
    }
}