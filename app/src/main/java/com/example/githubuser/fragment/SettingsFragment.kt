package com.example.githubuser.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.githubuser.R
import com.example.githubuser.Utils
import com.example.githubuser.receivers.NotificationReceiver
import com.example.githubuser.receivers.NotificationReceiver.Companion.NOTIF_TYPE
import java.util.*
import kotlin.collections.ArrayList

class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    private lateinit var appContext: Context
    private lateinit var dailyNotification: String
    private lateinit var dailyPreferences: SwitchPreferenceCompat
    private lateinit var releasePreferences: SwitchPreferenceCompat

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appContext = context
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        dailyNotification = appContext.resources.getString(R.string.daily_key)
        dailyPreferences = findPreference<Preference>(dailyNotification) as SwitchPreferenceCompat
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when(key){
            //daily
            dailyNotification -> {
                if (dailyPreferences.isChecked){
                   val calendar = Utils.getCalendar(9, 0, 1)
                    enableNotification(dailyNotification, Utils.dailyBroadcastID, calendar)
                }else{
                    disableNotification(Utils.dailyBroadcastID)
                }
            }
        }
    }

    private fun enableNotification(type: String, broadcastID: Int, time: Calendar){
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, NotificationReceiver::class.java)
        intent.putExtra(NOTIF_TYPE, type)
        val pendingIntent = PendingIntent.getBroadcast(appContext, broadcastID, intent, 0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, time.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun disableNotification(broadcastID: Int){
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(appContext, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(appContext, broadcastID, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }
}