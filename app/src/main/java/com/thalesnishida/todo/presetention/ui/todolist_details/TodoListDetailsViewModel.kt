package com.thalesnishida.todo.presetention.ui.todolist_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.R
import com.thalesnishida.todo.domain.usecase.GetTodoByIdUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoStatusUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class TodoListDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getTodoByIdUseCase: GetTodoByIdUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val updateTodoStatus: UpdateTodoStatusUseCase,
) : ViewModel() {

    private val todoId: String = checkNotNull(savedStateHandle["todoId"])

    val uiState: StateFlow<TodoListDetailsUiState> = getTodoByIdUseCase(todoId)
        .map { todo ->
            if (todo != null) {
                TodoListDetailsUiState(detailsState = TodoListDetailsState.Success(todo))
            } else {
                TodoListDetailsUiState(detailsState = TodoListDetailsState.Error("Todo not found"))
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TodoListDetailsUiState(detailsState = TodoListDetailsState.Loading)
        )

    private val _sideEffects = MutableSharedFlow<TodoListDetailsSideEffect>()
    val sideEffects: SharedFlow<TodoListDetailsSideEffect> = _sideEffects.asSharedFlow()
    fun processIntent(intent: TodoDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is TodoDetailsIntent.UpdateTodoDetails -> updateTodoDetails(intent)

                is TodoDetailsIntent.FinishTodo -> finishTodo()
            }
        }
    }

    private fun updateTodoDetails(intent: TodoDetailsIntent.UpdateTodoDetails) {
        viewModelScope.launch {
            updateTodoUseCase(
                todoId = todoId,
                title = intent.title,
                description = intent.description,
                isCompleted = intent.isCompleted
            )

            _sideEffects.emit(TodoListDetailsSideEffect.ShowToast(R.string.task_success_update))
        }
    }

    private suspend fun finishTodo() {
        try {
            updateTodoStatus(todoId = todoId, isCompleted = true)
            _sideEffects.emit(TodoListDetailsSideEffect.NavigateToHome)
        } catch (e: Exception) {
            _sideEffects.emit(TodoListDetailsSideEffect.ShowToast(R.string.task_error_update))
        }
    }
}