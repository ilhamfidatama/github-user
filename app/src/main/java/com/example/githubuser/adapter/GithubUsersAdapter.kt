package com.example.githubuser.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.DetailUserActivity
import com.example.githubuser.R
import com.example.githubuser.helper.Utils
import com.example.githubuser.model.GithubUser
import kotlinx.android.synthetic.main.user_github_list.view.*

class GithubUsersAdapter(val context: Context): RecyclerView.Adapter<GithubUsersAdapter.ViewHolder>() {
    private var listGithubUsers = arrayListOf<GithubUser>()

    fun addUsers(users: ArrayList<GithubUser>?){
        listGithubUsers.clear()
        users?.let { listGithubUsers.addAll(it) }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.user_github_list, parent, false))

    override fun getItemCount(): Int = listGithubUsers.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.e("adapter-$position", "${listGithubUsers[position]}")
        holder.bind(listGithubUsers[position])
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(user: GithubUser){
            itemView.name.text = user.login
            val glide = Utils.loadImage(context, user.avatar_url)
            glide.into(itemView.avatar_user)
            itemView.setOnClickListener {
                val intent = Intent(context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USER_PROFILE, user)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }
}