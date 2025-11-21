package com.thalesnishida.todo.presetention.ui.home

sealed class HomeIntent {
    object LoadTodos : HomeIntent()
    data class AddTodo(
        val title: String,
        val description: String? = "",
        val timestamp: Long? = null
    ) : HomeIntent()

    data class ToggleTodoStatus(val todoId: String, val isCompleted: Boolean) : HomeIntent()

    data class DeleteTodo(val todoId: String) : HomeIntent()
}