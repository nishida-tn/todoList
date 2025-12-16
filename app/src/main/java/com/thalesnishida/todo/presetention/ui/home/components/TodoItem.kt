package com.thalesnishida.todo.presetention.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.presetention.components.Category
import com.thalesnishida.todo.presetention.components.Priority
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TodoItem(
    todo: Todo,
    onToggleStatus: (String, Boolean) -> Unit,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth().padding(16.dp),
        onClick = { onClick(todo.id) },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(
                selected = todo.isCompleted,
                onClick = { onToggleStatus(todo.id, !todo.isCompleted) }
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null
                )

                Spacer(modifier = Modifier.width(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val dateText = remember(todo.scheduler) {
                        todo.scheduler?.let { formatTimestamp(it) } ?: "Sem agendamento"
                    }

                    Text(
                        text = dateText,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        todo.category?.let { Category(it) }
                        todo.priority?.let { Priority(it) }
                    }
                }
            }
        }
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val now = Calendar.getInstance()
    val taskDate = Calendar.getInstance().apply { timeInMillis = timestamp }
    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

    return timeFormatter.format(taskDate.time)
}

@Preview
@Composable
fun TodoItemPreview() {
    MaterialTheme {
        TodoItem(
            todo = Todo(
                id = "1",
                title = "Comprar leite",
                description = "Ir ao supermercado e comprar leite",
                isCompleted = true,
                createdAt = "2024-06-01T10:00:00Z",
                scheduler = null,
                priority = 1,
                category = null,
                categoryBackgroundColor = null,
                categoryColor = null,
                categoryIcon = null,
            ),
            onToggleStatus = { _, _ -> },
            onClick = { _ -> }
        )
    }
}