package com.thalesnishida.todo.presetention.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thalesnishida.todo.domain.model.Todo
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun TodoItem(
    todo: Todo,
    onToggleStatus: (String, Boolean) -> Unit,
    onDelete: (String) -> Unit,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Gray)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(todo.id) }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = todo.isCompleted,
                    onClick = { onToggleStatus(todo.id, !todo.isCompleted) }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(text = todo.title, style = MaterialTheme.typography.bodyLarge)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        todo.timestamp?.let {
                            val schedule = formatTimestamp(it)
                            Text("Agendado para: $schedule")
                        }
                        Row {

                        }
                    }
                }
            }

            IconButton(onClick = { onDelete(todo.id) }) {
                Icon(Icons.Default.Delete, contentDescription = "Excluir")
            }
        }

        if (todo.isCompleted) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 2.dp,
                modifier = Modifier
                    .padding(start = 52.dp, end = 48.dp)
                    .align(Alignment.Center)
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    TodoItem(
        todo = Todo(
            id = "1",
            title = "Comprar leite",
            description = "Ir ao supermercado e comprar leite",
            isCompleted = true,
            createdAt = "2024-06-01T10:00:00Z",
            timestamp = null
        ),
        onToggleStatus = { _, _ -> },
        onDelete = { _ -> },
        onClick = { _ -> }
    )
}

private fun formatTimestamp(timestamp: Long) : String {
    val now = Calendar.getInstance()
    val taskDate = Calendar.getInstance().apply { timeInMillis = timestamp }

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val timeString = timeFormatter.format(taskDate.time)

    val isSameYear = now.get(Calendar.YEAR) == taskDate.get(Calendar.YEAR)
    val isSameDay = isSameYear && now.get(Calendar.DAY_OF_YEAR) == taskDate.get(Calendar.DAY_OF_YEAR)

    val isTomorrow = isSameYear && now.get(Calendar.DAY_OF_YEAR) + 1 == taskDate.get(Calendar.DAY_OF_YEAR)

    return when {
        isSameDay -> "Hoje as $timeString"
        isTomorrow -> "Amanhã as $timeString"
        else -> {
            val fullDateFormatter = SimpleDateFormat("dd/MM/yyyy 'as' HH:mm", Locale.getDefault())
            fullDateFormatter.format(taskDate.time)
        }
    }
}
