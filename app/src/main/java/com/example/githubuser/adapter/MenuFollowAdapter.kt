package com.example.githubuser.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.githubuser.fragment.FollowersFragment
import com.example.githubuser.fragment.FollowingFragment

class MenuFollowAdapter(private val menu: Array<String>,
                        private val username: String,
                        fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0 -> fragment = FollowersFragment().apply {
                usernameFollowers = username
            }
            1 -> fragment = FollowingFragment().apply {
                usernameFollowing = username
            }
        }
        return fragment
    }

    override fun getCount(): Int = menu.size

    override fun getPageTitle(position: Int): CharSequence? = menu[position]
}