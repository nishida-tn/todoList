package com.thalesnishida.todo.presetention.ui.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thalesnishida.todo.R
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.presetention.components.ButtonDefault

@Composable
fun DetailsDialog(
    todo: Todo,
    onDismiss: () -> Unit,
    onConfirmEdit: (String, String) -> Unit
) {
    var title by rememberSaveable { mutableStateOf(todo.title) }
    var description by rememberSaveable { mutableStateOf(todo.description ?: "") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        shape = RoundedCornerShape(4.dp),
        title = {
            Column {
                Text(
                    "Edit Task Title",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = {
                        Text(
                            text = stringResource(R.string.task_title),
                            fontSize = 18.sp
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent
                    )
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = {
                        Text(
                            text = stringResource(R.string.task_description),
                            fontSize = 18.sp
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }

        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ButtonDefault(
                    showBackgroundColor = false,
                    onClick = {
                        onDismiss()
                    },
                    text = "Cancel",
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                )

                ButtonDefault(
                    onClick = {
                        onConfirmEdit(title, description)
                    },
                    text = "Edit",
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DetailsDialogPreview() {
    DetailsDialog(
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
        onDismiss = {},
        onConfirmEdit = { _, _ -> }
    )
}