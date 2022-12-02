package com.example.testappandroid.Notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.testappandroid.MainActivity
import com.example.testappandroid.MainActivity2
import com.example.testappandroid.R
import kotlinx.coroutines.MainScope


class MyReceiver : BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent?) {
        val task = MainActivity()

        val intent1 = Intent(context, MainActivity2::class.java)
        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,intent1,PendingIntent.FLAG_IMMUTABLE)

        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val pattern = longArrayOf(500, 500, 500, 500, 500, 500, 500, 500, 500)

        val builder = NotificationCompat.Builder(context,"TaskManagement")

            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("TaskManager")
            .setContentText("Manage to handle some task")
            //.setDefaults(NotificationCompat.DEFAULT_ALL)
            //.setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(pattern)
            //.setStyle(NotificationCompat.InboxStyle())
            //.setSound(uri)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        //val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build())

    }
}