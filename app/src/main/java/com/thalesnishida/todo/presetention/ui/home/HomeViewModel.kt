package com.thalesnishida.todo.presetention.ui.home

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.core.utils.AlarmScheduler
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.domain.usecase.AddTodoUseCase
import com.thalesnishida.todo.domain.usecase.DeleteTodoUseCase
import com.thalesnishida.todo.domain.usecase.GetTodosUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoStatusUseCase
import com.thalesnishida.todo.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.TimeZone

@HiltViewModel
class HomeViewModel @Inject constructor(
    @ApplicationContext context: Context,
    getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoStatusUseCase: UpdateTodoStatusUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase
) : ViewModel() {
    private val _draftScheduledTimestamp = MutableStateFlow<Long?>(null)
    private val _activeDialog = MutableStateFlow<HomeDialogState>(HomeDialogState.None)
    val uiState: StateFlow<HomeState> = combine(
        getTodosUseCase(),
        _draftScheduledTimestamp,
        _activeDialog
    ) { todo, draftTIme, dialogState ->
        HomeState(
            draftScheduledTimestamp = draftTIme,
            activeDialog = dialogState,
            listTodoState = if (todo.isEmpty()) {
                ListTodoState.Success(emptyList())
            } else {
                ListTodoState.Success(todo)
            }
        )
    }
        .catch { e ->
            emit(
                HomeState(
                    listTodoState = ListTodoState.Error(
                        e.localizedMessage ?: "Erro desconhecido"
                    )
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = HomeState(listTodoState = ListTodoState.Loading)
        )

    private val _sideEffects = MutableSharedFlow<HomeSideEffect>()
    val sideEffect: SharedFlow<HomeSideEffect> = _sideEffects.asSharedFlow()

    val alarmScheduler = AlarmScheduler(context)

    @OptIn(ExperimentalMaterial3Api::class)
    fun processIntent(intent: HomeIntent) {
        viewModelScope.launch {
            when (intent) {
                is HomeIntent.AddTodo -> addTodo(
                    intent.title, intent.description, intent.timestamp,
                    intent.category, intent.priority,
                    intent.categoryBackgroundColor, intent.categoryColor
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

                is HomeIntent.ClearDraftTime -> _draftScheduledTimestamp.value = null

                is HomeIntent.UpdateTodo -> updateTodo(intent.id, intent.title, intent.description)
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

        _draftScheduledTimestamp.value = localCalendar.timeInMillis
    }

    private fun groupTodosByDate(todos: List<Todo>) {

    }

    fun openDialog(todoId: String) {
        _activeDialog.value = HomeDialogState.DetailTodo(todoId)
    }

    fun onAddTodoClick() {
        _activeDialog.value =  HomeDialogState.AddTodoDialog
    }
    fun closeDialog() {
        _activeDialog.value = HomeDialogState.None
    }

    private fun updateTodo(id: String, title: String, description: String) {
        viewModelScope.launch {
            try {
                updateTodoUseCase(
                    todoId = id,
                    title = title,
                    description = description,
                )

                _activeDialog.value = HomeDialogState.None

                _sideEffects.emit(HomeSideEffect.ShowToast("Tarefa atualizada com sucesso!"))

            } catch (e: Exception) {
                _sideEffects.emit(HomeSideEffect.ShowToast("Erro ao salvar: ${e.localizedMessage}"))
            }
        }
    }

    private suspend fun addTodo(
        title: String,
        description: String?,
        timestamp: Long?,
        category: String?,
        priority: Int?,
        bgColor: Int?,
        color: Int?
    ) {
        try {
            addTodoUseCase(title, description, timestamp, category, priority, bgColor, color)

            timestamp?.let {
                alarmScheduler.scheduleAlarm(
                    todoId = it.toInt(),
                    title = title,
                    description = description ?: "",
                    timestamp = it
                )
                _sideEffects.emit(HomeSideEffect.ShowToast("Lembrete agendado!"))
            }

            _draftScheduledTimestamp.value = null

        } catch (e: Exception) {
            _sideEffects.emit(HomeSideEffect.ShowToast("Erro ao salvar: ${e.localizedMessage}"))
        }
    }

    private suspend fun toggleTodoStatus(todoId: String, isCompleted: Boolean) {
        try {
            updateTodoStatusUseCase(todoId, isCompleted)
        } catch (e: Exception) {
            _sideEffects.emit(HomeSideEffect.ShowToast("Erro ao atualizar: ${e.localizedMessage}"))
        }
    }

    private suspend fun deleteTodo(todoId: String) {
        try {
            deleteTodoUseCase(todoId)
        } catch (e: Exception) {
            _sideEffects.emit(HomeSideEffect.ShowToast("Erro ao deletar: ${e.localizedMessage}"))
        }
    }
}
