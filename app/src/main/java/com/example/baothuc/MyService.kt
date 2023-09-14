package com.example.baothuc

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class MyService:Service() {

    private lateinit var player: MediaPlayer

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.e("onService","Xin chao")
        player = MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI)
        player.start()
        return START_NOT_STICKY
    }
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
    }
}