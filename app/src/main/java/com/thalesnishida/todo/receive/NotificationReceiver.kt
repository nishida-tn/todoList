package com.thalesnishida.todo.receive

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.thalesnishida.todo.MainActivity
import com.thalesnishida.todo.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("TITLE") ?: "Tarefa"
        val description = intent.getStringExtra("DESCRIPTION") ?: "Hora de realizar sua tarefa!"
        val todoId = intent.getIntExtra("ID", 0)

        showNotification(context, title, description, todoId)
    }

    private fun showNotification(context: Context, title: String, description: String, id: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "todo_channel_alarm"
        val largeIconBitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher)
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Lembretes com Alarme",
                NotificationManager.IMPORTANCE_HIGH
            )

            val audioAttributes = AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build()

            channel.setSound(alarmSound, audioAttributes)
            channel.enableVibration(true)

            notificationManager.createNotificationChannel(channel)
        }

        val channel = NotificationChannel(
            channelId,
            "Lembretes de Tarefas",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(channel)

        val contentIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context, id, contentIntent, PendingIntent.FLAG_IMMUTABLE
        )


        val notification = NotificationCompat.Builder(context, channelId)
            .setLargeIcon(largeIconBitmap)
            .setSmallIcon(R.drawable.ic_timer)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setContentIntent(pendingIntent)
            .setSound(alarmSound)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(id, notification)
    }
}