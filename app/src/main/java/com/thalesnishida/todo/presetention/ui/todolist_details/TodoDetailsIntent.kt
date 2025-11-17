package com.thalesnishida.todo.presetention.ui.todolist_details

sealed class TodoDetailsIntent {
    data class LoadTodoDetails(val todoId: String) : TodoDetailsIntent()
    data class UpdateTodoDetails(
        val todoId: String,
        val title: String,
        val description: String?,
        val isCompleted: Boolean
    ) : TodoDetailsIntent()

    data class FinishTodo(
        val todoId: String,
    ) : TodoDetailsIntent()
}