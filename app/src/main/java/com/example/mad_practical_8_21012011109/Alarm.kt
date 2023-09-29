package com.example.mad_practical_8_21012011109

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class Alarm : BroadcastReceiver() {
    companion object{
        val Alaemkey = "key"
        val AlarmStart = "START"
        val AlarmStop = "STOP"
    }

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val data = intent.getStringExtra(Alaemkey)
        val intentService = Intent(context,AlarmService::class.java)
        if(data == AlarmStart){
            context.startService(intentService)
        }
        else{
            context.stopService(intentService)
        }


    }
}