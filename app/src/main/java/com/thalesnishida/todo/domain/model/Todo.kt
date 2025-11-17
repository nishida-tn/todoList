package com.thalesnishida.todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: Long,
)
