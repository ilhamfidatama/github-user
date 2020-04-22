package com.example.githubuser.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.R
import com.example.githubuser.Utils
import com.example.githubuser.model.GithubUser
import kotlinx.android.synthetic.main.user_github_list.view.*

class UsersFollowAdapter(val context: Context): RecyclerView.Adapter<UsersFollowAdapter.ViewHolder>() {
    private var listUsers = arrayListOf<GithubUser>()

    fun addUsers(users: ArrayList<GithubUser>){
        listUsers.clear()
        listUsers.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.user_github_list, parent, false))

    override fun getItemCount(): Int = listUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUsers[position])
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(user: GithubUser){
            itemView.name.text = user.login
            val glide = Utils.loadImage(context, user.avatar_url)
            glide.into(itemView.avatar_user)
        }
    }
}