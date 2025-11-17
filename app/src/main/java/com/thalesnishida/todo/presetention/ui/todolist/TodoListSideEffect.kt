package com.thalesnishida.todo.presetention.ui.todolist

sealed class TodoListSideEffect {
    data class ShowToast(val message: String) : TodoListSideEffect()
    data class NavigateToDetail(val todoId: Long) : TodoListSideEffect()
    object ScrollToTop : TodoListSideEffect()
}