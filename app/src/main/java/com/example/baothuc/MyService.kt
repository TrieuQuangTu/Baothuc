package com.example.baothuc

import android.app.Notification.Action
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyService:Service() {

    private lateinit var player :MediaPlayer
    private val channelId = "ForegroundChannel"
    private val notificationId = 123
    override fun onCreate() {
        super.onCreate()
        initMusic()
        creatNotificationChannel()
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent?.action =="stop"){
            stopForeground(true)
            stopSelf()
            return START_NOT_STICKY // sẽ tiếp tục hiển thị thông báo sau khi tắt nhạc
        }
        showNotification()
        return START_STICKY
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        player.stop()
        player.release()
        super.onDestroy()
    }
    private fun initMusic(){
        player = MediaPlayer.create(this,R.raw.nhacchuong)
        player.start()
    }


    private fun creatNotificationChannel(){

        //bắt buộc phải tạo notificationChannel khi sử dụng API >26
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId,"ForegroundChannel", NotificationManager.IMPORTANCE_DEFAULT)

            val notificationManager =getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
    private fun showNotification(){

        val intent =Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_IMMUTABLE)

        val stopIntent = Intent(this,MyService::class.java)
        stopIntent.action = "stop"
        val stopPendingIntent = PendingIntent.getService(this,0,stopIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val builder =NotificationCompat.Builder(this,channelId)
            .setContentTitle("Báo thức  ") //tiêu đề
            .setContentText("Đã đến giờ hẹn rồi") //Nội dung
            .setSmallIcon(R.drawable.baseline_notifications_24) //Icon
            .setContentIntent(pendingIntent) //Click vào thông bao sẽ chuyển đến component đã xác định ở intent
            .setAutoCancel(true) // lệnh này sẽ hủy thông báo khi ta click vào notification
            .addAction(R.drawable.baseline_pause_24,"Stop",stopPendingIntent) //thêm action
            .build()
        startForeground(notificationId,builder)

    }

}