package com.example.githubuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubuser.fragment.SettingsFragment

class AppsSettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}