package com.thalesnishida.todo.extensions

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun Long.convertToTimeString() : String {
    val taskDate = Instant.ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalTime()

    val today = LocalDate.now()

    val dayDiff = ChronoUnit.DAYS.between(taskDate, today)

    return when {
        dayDiff == 0L -> "Hoje"
        dayDiff == 1L -> "Ontem"
        dayDiff < 7L -> "Há $dayDiff dias"
        else -> taskDate.toString()
    }
}