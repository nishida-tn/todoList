package com.thalesnishida.todo.presetention.ui.home.components

import androidx.compose.runtime.Composable
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.presetention.ui.home.HomeDialogState
import com.thalesnishida.todo.presetention.ui.home.HomeIntent

@Composable
fun HomeDialogManager(
    dialogState: HomeDialogState,
    todos: List<Todo>,
    onDismiss: () -> Unit,
    onSaveEdit: (String, String, String) -> Unit,
    onAddTodo: (String, String, Long, String?, Int?) -> Unit,
    draftScheduledTimestamp: Long?,
    onDateTimeConfirmed: (date: Long, hour: Int, minute: Int) -> Unit,
) {
    when (dialogState) {
        is HomeDialogState.None -> Unit
        is HomeDialogState.DetailTodo -> {
            val todoToEdit = todos.find { it.id == dialogState.todoId }

            if (todoToEdit != null) {
                DetailsDialog(
                    todo = todoToEdit,
                    onDismiss = onDismiss,
                    onConfirmEdit = { newTitle, newDesc ->
                        onSaveEdit(todoToEdit.id, newTitle, newDesc)
                        onDismiss()
                    }
                )
            } else {
                onDismiss()
            }
        }
        is HomeDialogState.AddTodoDialog -> {
            AddTodoDialog(
                scheduledTimestamp = draftScheduledTimestamp,
                onDismiss = onDismiss,
                onAddTodo = { title, description, _, category, priority ->
                    onAddTodo(title, description, draftScheduledTimestamp ?: 0L, category, priority)
                    onDismiss()
                },
                onDateTimeConfirmed = { date, hour, minute ->
                    onDateTimeConfirmed(date, hour, minute)
                },
                onNavigateToCreateCategory = { },
                onShowCategoryChooseChange = {  },
                onCategorySelected = {
                },
            )
        }
    }
}