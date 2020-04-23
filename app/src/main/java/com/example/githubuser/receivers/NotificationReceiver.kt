package com.example.githubuser.receivers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.githubuser.R
import com.example.githubuser.Utils

class NotificationReceiver: BroadcastReceiver() {
    private val CHANNEL_ID = "githubUsers01"
    private val CHANNEL_NAME = "Github Users Channel"

    companion object{
        const val NOTIF_TYPE = "NOTIFICATION_TYPE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(NOTIF_TYPE)
        val dailyType = context.resources.getString(R.string.daily_key)
        if (type == dailyType){
            createDailyNotification(context)
        }
    }

    private fun createDailyNotification(context: Context){
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.daily_notification_text))
            .setSmallIcon(R.drawable.ic_update_icon_24dp)
            .setAutoCancel(true)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManager.notify(Utils.dailyBroadcastID, notification)
    }
}