package com.example.githubuser.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.adapter.UsersFollowAdapter
import com.example.githubuser.presenters.ApiPresenter
import kotlinx.android.synthetic.main.follow_fragment.*

class FollowingFragment: Fragment() {
    private lateinit var appContext: Context
    lateinit var adapter: UsersFollowAdapter
    var usernameFollowing: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UsersFollowAdapter(appContext)
        adapter.notifyDataSetChanged()
        usernameFollowing?.let { loadFollowingUsers(it) }
        list_users.adapter = adapter
        list_users.setHasFixedSize(true)
        list_users.layoutManager = LinearLayoutManager(appContext)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.follow_fragment, container, false)

    private fun loadFollowingUsers(username: String){
        progressBar.visibility = View.VISIBLE
        val apiPresenter = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ApiPresenter::class.java)
        apiPresenter.followingUsers(username)
        apiPresenter.getListUsers().observe(this, Observer {
            progressBar.visibility = View.GONE
            adapter.addUsers(it)
        })
    }
}