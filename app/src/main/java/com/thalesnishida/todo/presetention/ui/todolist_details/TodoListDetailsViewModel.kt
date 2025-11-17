package com.thalesnishida.todo.presetention.ui.todolist_details

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.R
import com.thalesnishida.todo.domain.usecase.GetTodoByIdUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoStatusUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    @ApplicationContext private val context: Context,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val updateTodoStatus: UpdateTodoStatusUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TodoListDetailsState())
    val uiState: StateFlow<TodoListDetailsState> = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<TodoListDetailsSideEffect>()
    val sideEffects: SharedFlow<TodoListDetailsSideEffect> = _sideEffects.asSharedFlow()
    fun processIntent(intent: TodoDetailsIntent) {
        when (intent) {
            is TodoDetailsIntent.LoadTodoDetails -> loadTodoDetails(intent.todoId)
            is TodoDetailsIntent.UpdateTodoDetails -> updateTodoDetails(
                TodoDetailsIntent.UpdateTodoDetails(
                    intent.todoId,
                    intent.title,
                    intent.description,
                    intent.isCompleted
                )
            )

            is TodoDetailsIntent.FinishTodo -> updateTodoStatus(
                TodoDetailsIntent.FinishTodo(
                    intent.todoId,
                )
            )
        }
    }

    private fun loadTodoDetails(todoId: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
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

            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message) }
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun updateTodoDetails(intent: TodoDetailsIntent.UpdateTodoDetails) {
        viewModelScope.launch {
            updateTodoUseCase.invoke(
                todoId = intent.todoId,
                title = intent.title,
                description = intent.description,
                isCompleted = intent.isCompleted
            )

            _sideEffects.emit(TodoListDetailsSideEffect.ShowToast(context.getString(R.string.task_success_update)))
        }
    }

    private fun updateTodoStatus(intent: TodoDetailsIntent.FinishTodo) {
        viewModelScope.launch {
            updateTodoStatus.invoke(
                todoId = intent.todoId,
                isCompleted = true
            )
            _sideEffects.emit(TodoListDetailsSideEffect.NavigateToHome)
        }
    }
}