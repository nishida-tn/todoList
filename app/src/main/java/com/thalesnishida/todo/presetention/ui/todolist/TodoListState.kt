package com.thalesnishida.todo.presetention.ui.todolist

import com.thalesnishida.todo.domain.model.Todo

data class TodoListState(
    val todos: List<Todo> = emptyList(),
    val isLoading: Boolean = false,
    val isAddingTodo: Boolean = false,
    val error: String? = null,
    val showDialog: Boolean = false,
) {
    val isEmpty: Boolean get() = todos.isEmpty()
}