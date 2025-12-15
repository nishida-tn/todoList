package com.thalesnishida.todo.presetention.ui.home

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.domain.usecase.AddTodoUseCase
import com.thalesnishida.todo.domain.usecase.DeleteTodoUseCase
import com.thalesnishida.todo.domain.usecase.GetTodosUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoStatusUseCase
import com.thalesnishida.todo.utils.AlarmScheduler
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
import java.util.Calendar
import java.util.TimeZone

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoStatusUseCase: UpdateTodoStatusUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<HomeSideEffect>()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffects.asSharedFlow()

    val alarmScheduler = AlarmScheduler(context)


    init {
        processIntent(HomeIntent.LoadTodos)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun processIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeIntent.LoadTodos -> loadTodos()
                is HomeIntent.AddTodo -> addTodo(
                    title = intent.title,
                    description = intent.description,
                    timestamp = intent.timestamp,
                    category = intent.category,
                    priority = intent.priority,
                    categoryBackgroundColor = intent.categoryBackgroundColor,
                    categoryColor = intent.categoryColor
                )

                is HomeIntent.ToggleTodoStatus -> toggleTodoStatus(
                    intent.todoId,
                    intent.isCompleted
                )

                is HomeIntent.DeleteTodo -> deleteTodo(intent.todoId)
                is HomeIntent.UpdateDraftTime -> calculateTimestamp(
                    intent.dateMillis,
                    intent.hour,
                    intent.minute
                )

                is HomeIntent.ClearDraftTime -> _uiState.update { it.copy(draftScheduledTimestamp = null) }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    private fun calculateTimestamp(dateMillis: Long, hour: Int, minute: Int) {
        val utcCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        utcCalendar.timeInMillis = dateMillis

        val year = utcCalendar.get(Calendar.YEAR)
        val month = utcCalendar.get(Calendar.MONTH)
        val day = utcCalendar.get(Calendar.DAY_OF_MONTH)

        val localCalendar = Calendar.getInstance()
        localCalendar.set(year, month, day, hour, minute, 0)
        localCalendar.set(Calendar.MILLISECOND, 0)

        val finalTimestamp = localCalendar.timeInMillis

        _uiState.update {
            it.copy(draftScheduledTimestamp = finalTimestamp)
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
                    HomeSideEffect.ShowToast("Falha ao carregar tarefas: ${e.localizedMessage}")
                )
            }
        }
    }

    private fun groupTodosByDate(todos: List<Todo>) {
        // Implementation can be added here if needed
    }

    private fun addTodo(
        title: String,
        description: String? = "",
        timestamp: Long? = null,
        category: String? = null,
        priority: Int? = null,
        categoryBackgroundColor: Int? = null,
        categoryColor: Int? = null
    ) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            try {
                addTodoUseCase(
                    title,
                    description,
                    timestamp,
                    category,
                    priority,
                    categoryBackgroundColor,
                    categoryColor
                )
                timestamp?.let {
                    val alarmId = timestamp.toInt()

                    alarmScheduler.scheduleAlarm(
                        todoId = alarmId,
                        title = title,
                        description = description ?: "",
                        timestamp = timestamp
                    )

                    HomeSideEffect.ShowToast("Lembrete agendado!")
                }
                processIntent(HomeIntent.LoadTodos)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.localizedMessage ?: "Erro ao adicionar a tarefa"
                    )
                }
                _sideEffects.emit(HomeSideEffect.ShowToast("Falha ao adicionar tarefa: ${e.localizedMessage}"))
            }
        }
    }

    private fun toggleTodoStatus(todoId: String, isCompleted: Boolean) {
        viewModelScope.launch {
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
                processIntent(HomeIntent.LoadTodos)
                _sideEffects.emit(HomeSideEffect.ShowToast("Falha ao atualizar status: ${e.localizedMessage}"))
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
                _sideEffects.emit(HomeSideEffect.ShowToast("Falha ao deletar tarefa: ${e.localizedMessage}"))
            }
        }
    }


}
