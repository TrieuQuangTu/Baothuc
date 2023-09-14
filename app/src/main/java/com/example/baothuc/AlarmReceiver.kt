package com.example.baothuc

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
class AlarmReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context?, intent: Intent?) {
        val i2 = Intent(context,MyService::class.java)
        context!!.startService(i2)

        //display notification
        val i = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)


        val builder = NotificationCompat.Builder(context, "tuandroid")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Bao thuc")
            .setContentText("Da den hen gio bao thuc")
            .setAutoCancel(true)
            .addAction(0,"Tat",pendingIntent)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val NotificationCompat = NotificationManagerCompat.from(context)
        NotificationCompat.notify(123, builder.build())

    }

}