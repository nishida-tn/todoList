package com.thalesnishida.todo.presetention.ui.todolist

sealed class TodoListIntent {
    object LoadTodos : TodoListIntent()
    data class AddTodo(val title: String, val description: String? = "") : TodoListIntent()
    data class ToggleTodoStatus(val todoId: String, val isCompleted: Boolean): TodoListIntent()
    data class DeleteTodo(val todoId: String) : TodoListIntent()
}