package com.thalesnishida.todo.presetention.ui.todolist_details

data class TodoListDetailsState(
    val title: String = "",
    val description: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: Long = 0L,
)
