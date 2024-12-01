package com.example.geofenceapp.internal.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import java.util.Random

fun showNotification(context: Context, title: String, message: String) {
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val notificationChannelId = "geofence_channel"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            notificationChannelId,
            "Geofence Notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, notificationChannelId)
        .setContentTitle(title)
        .setContentText(message)
        .setSmallIcon(androidx.core.R.drawable.ic_call_answer)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(Random().nextInt(), notification)
}
