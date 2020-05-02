package com.example.favoriteusers.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.favoriteusers.R
import com.example.favoriteusers.helper.Utils
import com.example.favoriteusers.model.GithubUser
import kotlinx.android.synthetic.main.list_users.view.*

class GithubUsersAdapter(val context: Context): RecyclerView.Adapter<GithubUsersAdapter.ViewHolder>() {
    private var listGithubUsers = arrayListOf<GithubUser>()

    fun addUsers(users: ArrayList<GithubUser>?){
        listGithubUsers.clear()
        users?.let { listGithubUsers.addAll(it) }
        notifyDataSetChanged()
    }

    fun getUsers() = listGithubUsers

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.list_users, parent, false))

    override fun getItemCount(): Int = listGithubUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("adapter-$position", "${listGithubUsers[position]}")
        holder.bind(listGithubUsers[position])
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(user: GithubUser){
            itemView.username.text = user.login
            val glide = Utils.loadImage(context, user.avatar_url)
            glide.into(itemView.avatar_user)
        }
    }
}