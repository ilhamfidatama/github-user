package com.example.githubuser

import android.annotation.SuppressLint
import android.content.ContentValues
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.adapter.MenuFollowAdapter
import com.example.githubuser.helper.Utils
import com.example.githubuser.model.GithubUser
import com.example.githubuser.presenters.ApiPresenter
import kotlinx.android.synthetic.main.activity_detail_user.*
import com.example.githubuser.contract.DatabaseContract.GithubUsersColumn.Companion.GITHUB_USERS_URI
import com.example.githubuser.contract.DatabaseContract.GithubUsersColumn.Companion.USERNAME
import com.example.githubuser.contract.DatabaseContract.GithubUsersColumn.Companion.AVATAR_URL

class DetailUserActivity : AppCompatActivity() {
    companion object{
        const val USER_PROFILE = "USER PROFILE"
    }
    private lateinit var menu: Array<String>
    private lateinit var uri: Uri
    private var isFavorite: Boolean = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        val userProfile = intent.getParcelableExtra(USER_PROFILE) as GithubUser
        loadDetailUser(userProfile.login)
        menu = resources.getStringArray(R.array.menu_follow)
        setFavorite(isFavorite)
        val menuAdapter = MenuFollowAdapter(menu, userProfile.login, supportFragmentManager)
        follow_content.adapter = menuAdapter
        menu_follow.setupWithViewPager(follow_content)
        uri = Uri.parse("$GITHUB_USERS_URI")
        btn_favorite.setOnClickListener {
            isFavorite = !isFavorite
            setFavorite(isFavorite)
            if (isFavorite) saveToFavoriteUsers(userProfile) else discardFromFavoriteUsers()
        }
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

    private fun setFavorite(favorite: Boolean){
        var drawableImage = R.drawable.ic_not_favorite_24dp
        if (favorite){
            drawableImage = R.drawable.ic_favorite_24dp
        }
        btn_favorite.setBackgroundResource(drawableImage)
    }

    private fun saveToFavoriteUsers(user: GithubUser){
        val values = ContentValues()
        values.put(USERNAME, user.login)
        values.put(AVATAR_URL, user.avatar_url)
        contentResolver.insert(GITHUB_USERS_URI, values)
    }

    private fun discardFromFavoriteUsers(){
        contentResolver.delete(uri, null, null)
    }
}