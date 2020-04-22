package com.example.githubuser.api.response

import com.example.githubuser.model.GithubUser

data class SearchResponse (
    var total_count: Int,
    var items: ArrayList<GithubUser>
)