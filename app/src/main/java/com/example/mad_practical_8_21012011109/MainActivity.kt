package com.example.mad_practical_8_21012011109

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextClock
import android.widget.Toast
import com.example.mad_practical_8_21012011109.R.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.text.SimpleDateFormat
import java.util.Calendar
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)

        val addAlarm : MaterialButton = findViewById(R.id.createalarm)
        val card : MaterialCardView = findViewById(R.id.materialcard2)

        card.visibility = View.GONE

        addAlarm.setOnClickListener {
            TimePickerDialog(this, {tp, hour, minute -> setAlarmTime(hour, minute) }, Calendar.getInstance().get(Calendar.HOUR), Calendar.getInstance().get(Calendar.MINUTE), false).show()
            card.visibility = View.VISIBLE
        }

        val cancelAlarm : MaterialButton = findViewById(R.id.cancelalarm)
        cancelAlarm.setOnClickListener {
            stop()
            card.visibility = View.GONE
        }
    }

    private fun setAlarmTime(hour : Int, minute : Int){
        val alarmtime = Calendar.getInstance()
        val year = alarmtime.get(Calendar.YEAR)
        val month = alarmtime.get(Calendar.MONTH)
        val date = alarmtime.get(Calendar.DATE)
        alarmtime.set(year, month, date, hour, minute, 0)
        val textAlarmTime : TextClock = findViewById(R.id.TextClock)
        textAlarmTime.text = SimpleDateFormat("hh:mm:ss a").format(alarmtime.time)
        setAlarm(alarmtime.timeInMillis, Alarm.AlarmStart)
        Toast.makeText(this, "Alarm Set", Toast.LENGTH_LONG).show()
    }

    fun stop() {
        setAlarm(-1, Alarm.AlarmStop)
    }

    fun setAlarm(militime : Long, action : String) {
        val intentalarm = Intent(applicationContext, Alarm::class.java)
        intentalarm.putExtra(Alarm.Alaemkey, action)
        val pendingintent = PendingIntent.getBroadcast(applicationContext, 4356, intentalarm, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        val alarmmanager = getSystemService(ALARM_SERVICE) as AlarmManager

        if (action == Alarm.AlarmStart) {
            alarmmanager.setExact(AlarmManager.RTC_WAKEUP, militime, pendingintent)
        }
        else if (action == Alarm.AlarmStop) {
            alarmmanager.cancel(pendingintent)
            sendBroadcast(intentalarm)
        }
    }
}