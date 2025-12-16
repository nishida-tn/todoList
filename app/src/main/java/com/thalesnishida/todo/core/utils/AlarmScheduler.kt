package com.thalesnishida.todo.core.utils

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.thalesnishida.todo.receive.NotificationReceiver

class AlarmScheduler(private val context: Context) {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleAlarm(todoId: Int, title: String, description: String, timestamp: Long) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("ID", todoId)
            putExtra("TITLE", title)
            putExtra("DESCRIPTION", description)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            todoId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            timestamp,
            pendingIntent
        )
    }
}