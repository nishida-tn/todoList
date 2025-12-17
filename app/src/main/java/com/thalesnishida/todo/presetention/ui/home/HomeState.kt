package com.thalesnishida.todo.presetention.ui.home

import com.thalesnishida.todo.domain.model.Todo

data class HomeState(
    val isAddingTodo: Boolean = false,
    val showDialog: Boolean = false,
    val draftScheduledTimestamp: Long? = null,
    val listTodoState: ListTodoState = ListTodoState.Loading,
    val activeDialog: HomeDialogState = HomeDialogState.None
)

sealed interface ListTodoState {
    data object Loading: ListTodoState
    data class Success(val todos: List<Todo>): ListTodoState
    data class Error(val message: String): ListTodoState
}

sealed interface HomeDialogState {
    data object None: HomeDialogState
    data object AddTodoDialog : HomeDialogState
    data class DetailTodo(val todoId: String): HomeDialogState
}