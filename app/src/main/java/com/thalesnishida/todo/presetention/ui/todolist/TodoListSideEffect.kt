package com.thalesnishida.todo.presetention.ui.todolist

sealed class TodoListSideEffect {
    data class ShowToast(val message: String) : TodoListSideEffect()
    object ScrollToTop : TodoListSideEffect()
}