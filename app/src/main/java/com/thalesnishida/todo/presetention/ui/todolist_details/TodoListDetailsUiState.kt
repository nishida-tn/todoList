package com.thalesnishida.todo.presetention.ui.todolist_details

import com.thalesnishida.todo.domain.model.Todo
import org.apache.commons.lang3.StringUtils
data class TodoListDetailsUiState(
    val detailsState: TodoListDetailsState = TodoListDetailsState.Loading,
)

sealed interface TodoListDetailsState {
    data object Loading : TodoListDetailsState
    data class Success(val todo: Todo) : TodoListDetailsState
    data class Error(val message: String) : TodoListDetailsState
}
