package com.thalesnishida.todo.presetention.ui.todolist_details

import org.apache.commons.lang3.StringUtils
data class TodoListDetailsState(
    val title: String = StringUtils.EMPTY,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: String = StringUtils.EMPTY,
    val error: String? = null,
    val isLoading: Boolean = false
)
