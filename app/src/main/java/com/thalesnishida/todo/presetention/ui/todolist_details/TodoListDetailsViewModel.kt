package com.thalesnishida.todo.presetention.ui.todolist_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.domain.usecase.GetTodoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class TodoListDetailsViewModel @Inject constructor(
    private val getTodoByIdUseCase: GetTodoByIdUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TodoListDetailsState())
    val uiState: StateFlow<TodoListDetailsState> = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<TodoListDetailsSideEffect>()
    val sideEffects: SharedFlow<TodoListDetailsSideEffect> = _sideEffects.asSharedFlow()

    fun processIntent(intent: TodoDetailsIntent) {
        when (intent) {
            is TodoDetailsIntent.LoadTodoDetails -> loadTodoDetails(intent.todoId)
        }
    }

    private fun loadTodoDetails(todoId: String) {
        viewModelScope.launch {
            val todo = getTodoByIdUseCase(todoId)
            todo?.let {
                _uiState.update {
                    it.copy(
                        title = todo.title,
                        description = todo.description,
                        isCompleted = todo.isCompleted,
                        createdAt = todo.createdAt
                    )
                }
            }
        }
    }
}