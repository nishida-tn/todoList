package com.thalesnishida.todo.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Todo(
    val id: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val scheduler: Long?,
    val createdAt: String,
    val priority: Int?,
    val category: String?,
    val categoryBackgroundColor: Int?,
    val categoryColor: Int?,
    val categoryIcon: String?
)
