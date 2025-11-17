package com.thalesnishida.todo.presetention.ui.todolist_details

sealed class TodoDetailsIntent {
    data class LoadTodoDetails(val todoId: String) : TodoDetailsIntent()
}