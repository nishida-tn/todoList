package com.thalesnishida.todo.extensions

import android.app.AlarmManager
import android.content.Context

fun Context.getAlarmManager() =
    getSystemService(Context.ALARM_SERVICE) as? AlarmManager