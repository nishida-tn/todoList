package com.thalesnishida.todo.presetention.ui.todolist_details

sealed class TodoListDetailsSideEffect {
    data class ShowToast(val message: String) : TodoListDetailsSideEffect()
}