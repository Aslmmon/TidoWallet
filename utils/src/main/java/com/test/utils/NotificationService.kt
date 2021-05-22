package com.test.utils

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.test.utils.App.Companion.context
import com.test.utils.Common.di.getSharedPrefrences


class NotificationService : FirebaseMessagingService() {


    @SuppressLint("CommitPrefEdits")
    override fun onNewToken(token: String) {
        Log.i("notification", token)
        getSharedPrefrences(androidApplication = App.getAppContext()).edit().putString(NOTIFICATION_TOKEN, token).apply()
        Log.i("notification", "notification saved ${getSharedPrefrences(androidApplication = App.getAppContext()).getString(NOTIFICATION_TOKEN, "")}")
        super.onNewToken(token)
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //  remoteMessage.mes == "general"

        remoteMessage.notification?.let {


            Log.i("notification", "Message Notification Body: ${it.body}")
            Log.i("notification", "Message Notification title: ${it.title}")
            val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java) as NotificationManager
            notificationManager.sendNotification(it.body.toString(), it.title.toString(), context)
        }
    }
}