package com.thalesnishida.todo.presetention.ui.home

import com.thalesnishida.todo.domain.model.Todo

data class HomeState(
    val isAddingTodo: Boolean = false,
    val showDialog: Boolean = false,
    val draftScheduledTimestamp: Long? = null,
    val listTodoState: ListTodoState = ListTodoState.Loading
)

sealed interface ListTodoState {
    data object Loading: ListTodoState
    data class Success(val todos: List<Todo>): ListTodoState
    data class Error(val message: String): ListTodoState
}