package com.thalesnishida.todo.presetention.ui.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = { onClick(todo.id) },
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF363636),
        ),
        shape = RoundedCornerShape(4.dp),
        border = if (isSelected) BorderStroke(1.dp, Color(0xFF809CFF)) else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = todo.isCompleted,
                onClick = { onToggleStatus(todo.id, !todo.isCompleted) },
                colors = RadioButtonDefaults.colors(
                    unselectedColor = Color.White,
                    selectedColor = Color(0xFF8687E7)
                ),
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    ),
                    color = Color.White,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null
                )

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
                        color = Color(0xFFAFAFAF)
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

    return if (now.get(Calendar.YEAR) == taskDate.get(Calendar.YEAR) &&
        now.get(Calendar.DAY_OF_YEAR) == taskDate.get(Calendar.DAY_OF_YEAR)
    ) {
        "Today At ${timeFormatter.format(taskDate.time)}"
    } else {
        timeFormatter.format(taskDate.time)
    }
}

@Preview
@Composable
fun TodoItemPreview() {
    MaterialTheme {
        TodoItem(
            todo = Todo(
                id = "1",
                title = "Do Math Homework",
                description = "Ir ao supermercado e comprar leite",
                isCompleted = false,
                createdAt = "2024-06-01T10:00:00Z",
                scheduler = Calendar.getInstance().apply { 
                    set(Calendar.HOUR_OF_DAY, 16)
                    set(Calendar.MINUTE, 45)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis,
                priority = 1,
                category = "University",
                categoryBackgroundColor = null,
                categoryColor = null,
                categoryIcon = null,
            ),
            onToggleStatus = { _, _ -> },
            onClick = { _ -> },
            isSelected = true
        )
    }
}