package com.thalesnishida.todo.presetention.ui.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.domain.usecase.AddTodoUseCase
import com.thalesnishida.todo.domain.usecase.DeleteTodoUseCase
import com.thalesnishida.todo.domain.usecase.GetTodosUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoStatusUseCase
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
class TodoListViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoStatusUseCase: UpdateTodoStatusUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListState())
    val uiState: StateFlow<TodoListState> = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<TodoListSideEffect>()
    val sideEffect: SharedFlow<TodoListSideEffect> = _sideEffects.asSharedFlow()

    init {
        processIntent(TodoListIntent.LoadTodos)
    }

    fun processIntent(intent: TodoListIntent) {
        viewModelScope.launch {
            when (intent) {
                is TodoListIntent.LoadTodos -> loadTodos()
                is TodoListIntent.AddTodo -> addTodo(
                    title = intent.title,
                    description = intent.description
                )

                is TodoListIntent.ToggleTodoStatus -> toggleTodoStatus(
                    intent.todoId,
                    intent.isCompleted
                )

                is TodoListIntent.DeleteTodo -> deleteTodo(intent.todoId)
            }
        }
    }

    private fun loadTodos() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                getTodosUseCase().collect { todos ->
                    _uiState.update { it.copy(todos = todos, isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Erro ao carregar tarefas"
                    )
                }
                _sideEffects.emit(
                    TodoListSideEffect.ShowToast("Falha ao carregar tarefas: ${e.localizedMessage}")
                )
            }
        }
    }

    private fun addTodo(title: String, description: String? = null) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                addTodoUseCase(title, description)
                processIntent(TodoListIntent.LoadTodos)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.localizedMessage ?: "Erro ao adicionar a tarefa"
                    )
                }
                _sideEffects.emit(TodoListSideEffect.ShowToast("Falha ao adicionar tarefa: ${e.localizedMessage}"))
            }
        }
    }

    private fun toggleTodoStatus(todoId: String, isCompleted: Boolean) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _uiState.update { currentState ->
                currentState.copy(
                    todos = currentState.todos.map { todo ->
                        if (todo.id == todoId) todo.copy(isCompleted = isCompleted) else todo
                    }
                )
            }
            try {
                updateTodoStatusUseCase(todoId, isCompleted)
            } catch (e: Exception) {
                processIntent(TodoListIntent.LoadTodos)
                _sideEffects.emit(TodoListSideEffect.ShowToast("Falha ao atualizar status: ${e.localizedMessage}"))
            }
        }
    }

    private fun deleteTodo(todoId: String) {
        viewModelScope.launch {
            val originalTodos = _uiState.value.todos
            _uiState.update { currentState ->
                currentState.copy(todos = currentState.todos.filter { it.id != todoId })
            }
            try {
                deleteTodoUseCase(todoId)
            } catch (e: Exception) {
                _uiState.update { it.copy(todos = originalTodos, error = e.localizedMessage) }
                _sideEffects.emit(TodoListSideEffect.ShowToast("Falha ao deletar tarefa: ${e.localizedMessage}"))
            }
        }
    }
}
