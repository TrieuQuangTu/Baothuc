package com.example.baothuc

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.baothuc.databinding.ActivityMainBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var picker:MaterialTimePicker
    private lateinit var calendar: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //1.click button select time
        binding.btnSelect.setOnClickListener {
            showTimepicker()
        }

        //click button set alarm
        binding.btnSet.setOnClickListener {
            setAlarm()
        }

        //click button cancel
        binding.btnCancel.setOnClickListener {

            cancelAlarm()
        }
    }

    private fun showTimepicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Chon thoi gian can hen gio")
            .build()

        picker.show(supportFragmentManager,"tuandroid")

        picker.addOnPositiveButtonClickListener {
            if(picker.hour>12){
                binding.tvSelect.text=String.format("%02d",(picker.getHour()-12)) +":"+ String.format("%02d", picker.getMinute())+"PM"
            }
            else
            {
                binding.tvSelect.text =String.format("%02d",(picker.getHour())) +":"+ String.format("%02d", picker.getMinute())+"AM"
            }

            calendar = Calendar.getInstance()
            calendar[Calendar.HOUR_OF_DAY] = picker.hour
            calendar[Calendar.MINUTE] = picker.minute
            calendar[Calendar.SECOND] = 0
            calendar[Calendar.MILLISECOND] = 0
        }
    }

    private fun setAlarm() {

        alarmManager =getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this@MainActivity,AlarmReceiver::class.java)
        pendingIntent=PendingIntent.getBroadcast(this@MainActivity,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(this,"Hen gio thanh cong",Toast.LENGTH_LONG).show()
    }

    private fun cancelAlarm() {

        alarmManager =getSystemService(ALARM_SERVICE) as AlarmManager
        val intent2 = Intent(this,AlarmReceiver::class.java)
        pendingIntent=PendingIntent.getBroadcast(this,0,intent2,PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.cancel(pendingIntent)

        //tham chieu toi MyService

        val intent22 =Intent(this@MainActivity,MyService::class.java)
        stopService(intent22)

        Toast.makeText(this,"Alarm cancel",Toast.LENGTH_SHORT).show()
    }

}