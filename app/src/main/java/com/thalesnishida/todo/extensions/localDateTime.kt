package com.thalesnishida.todo.extensions

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

fun LocalDateTime.toFormatterString(): String {
    return this.format(formatter)
}