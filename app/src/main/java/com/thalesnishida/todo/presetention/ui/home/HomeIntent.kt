package com.thalesnishida.todo.presetention.ui.home

sealed class HomeIntent {
    object LoadTodos : HomeIntent()
    data class AddTodo(
        val title: String,
        val description: String? = "",
        val timestamp: Long? = null,
        val category: String? = null,
        val priority: Int? = null,
        val categoryBackgroundColor: Int? = null,
        val categoryColor: Int? = null
    ) : HomeIntent()

    data class ToggleTodoStatus(val todoId: String, val isCompleted: Boolean) : HomeIntent()
    data class DeleteTodo(val todoId: String) : HomeIntent()
    data class UpdateDraftTime(val dateMillis: Long, val hour: Int, val minute: Int) : HomeIntent()
    data object ClearDraftTime : HomeIntent()
}