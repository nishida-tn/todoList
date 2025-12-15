package com.thalesnishida.todo.presetention.ui.home.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thalesnishida.todo.R
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
    onDelete: (String) -> Unit,
    onClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(colorResource(R.color.light_gray))
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick(todo.id) }
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            RadioButton(
                selected = todo.isCompleted,
                onClick = { onToggleStatus(todo.id, !todo.isCompleted) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            Column {
                Row {
                    Text(
                        color = Color.White,
                        text = todo.title,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    todo.scheduler?.let {
                        val schedule = formatTimestamp(it)
                        Text(
                            color = Color.Gray,
                            text = "Agendado para: $schedule"
                        )
                    } ?: Text("Sem agendamento", color = Color.Gray)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        todo.category?.let {
                            Category(it)
                        }

                        todo.priority?.let {
                            Priority(it)
                        }
                    }
                }

            }

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
            scheduler = null,
            priority = 1,
            category = null,
            categoryBackgroundColor = null,
            categoryColor = null,
            categoryIcon = null,
        ),
        onToggleStatus = { _, _ -> },
        onDelete = { _ -> },
        onClick = { _ -> }
    )
}

private fun formatTimestamp(timestamp: Long): String {
    val now = Calendar.getInstance()
    val taskDate = Calendar.getInstance().apply { timeInMillis = timestamp }

    val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val timeString = timeFormatter.format(taskDate.time)

    val isSameYear = now.get(Calendar.YEAR) == taskDate.get(Calendar.YEAR)
    val isSameDay =
        isSameYear && now.get(Calendar.DAY_OF_YEAR) == taskDate.get(Calendar.DAY_OF_YEAR)

    val isTomorrow =
        isSameYear && now.get(Calendar.DAY_OF_YEAR) + 1 == taskDate.get(Calendar.DAY_OF_YEAR)

    return when {
        isSameDay -> "Hoje as $timeString"
        isTomorrow -> "Amanhã as $timeString"
        else -> {
            val fullDateFormatter = SimpleDateFormat("dd/MM/yyyy 'as' HH:mm", Locale.getDefault())
            fullDateFormatter.format(taskDate.time)
        }
    }
}
