package com.thalesnishida.todo.presetention.ui.home

import com.thalesnishida.todo.domain.model.Todo

data class HomeState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val isAddingTodo: Boolean = false,
    val error: String? = null,
    val showDialog: Boolean = false,
    val draftScheduledTimestamp: Long? = null
) {
    val isEmpty: Boolean get() = todos.isEmpty()
}