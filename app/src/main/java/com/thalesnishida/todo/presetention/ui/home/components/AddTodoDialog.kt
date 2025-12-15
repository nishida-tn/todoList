package com.thalesnishida.todo.presetention.ui.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thalesnishida.todo.R
import com.thalesnishida.todo.extensions.getAlarmManager
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onAddTodo: (String, String, Long?, String?, Int?) -> Unit,
    showCategoryChoose: Boolean = false,
    onDateTimeConfirmed: (Long, Int, Int) -> Unit,
    scheduledTimestamp: Long?,
    onNavigateToCreateCategory: () -> Unit,
    onCategorySelected: (String) -> Unit,
    onShowCategoryChooseChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    context.getAlarmManager()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var categoryTodo by rememberSaveable { mutableStateOf<String?>(null) }
    var priorityTodo by rememberSaveable { mutableStateOf<Int?>(null) }
    var categoryBackgroundColor by rememberSaveable { mutableIntStateOf(0) }
    var categoryIcon by rememberSaveable { mutableStateOf(null) }

    var showDatePicker by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var showFlagChoose by rememberSaveable { mutableStateOf(false) }
    var showCategoryChoose by rememberSaveable { mutableStateOf(showCategoryChoose) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(),
        contentColor = MaterialTheme.colorScheme.onBackground,
        sheetState = sheetState,
        content = {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    "Adicionar nova tarefa",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
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
                            text = stringResource(R.string.description_task),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Start
                        )
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent
                    )
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            painter = painterResource(R.drawable.ic_timer),
                            contentDescription = "Timer Icon",
                            modifier = Modifier
                                .clickable {
                                    showDatePicker = true
                                }
                                .size(32.dp)
                        )
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            painter = painterResource(R.drawable.ic_tag),
                            contentDescription = "Timer Icon",
                            modifier = Modifier
                                .clickable {
                                    showCategoryChoose = true
                                }
                                .size(32.dp)
                        )
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            painter = painterResource(R.drawable.ic_flag),
                            contentDescription = "Timer Icon",
                            modifier = Modifier
                                .clickable {
                                    showFlagChoose = true
                                }
                                .size(32.dp)
                        )
                    }
                    Icon(
                        tint = MaterialTheme.colorScheme.primary,
                        painter = painterResource(R.drawable.ic_send),
                        contentDescription = "Send Icon",
                        modifier = Modifier
                            .clickable {
                                if (title.isNotBlank()) {
                                    onAddTodo(
                                        title,
                                        description,
                                        scheduledTimestamp,
                                        categoryTodo,
                                        priorityTodo
                                    )
                                }
                            }
                            .size(32.dp)
                    )
                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = onDismiss,
                        confirmButton = {
                            TextButton(onClick = {
                                showTimePicker = true
                            }) {
                                Text("Choose Time")
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = onDismiss) {
                                Text("Cancel")
                            }
                        }
                    ) {
                        DatePicker(state = datePickerState)
                    }
                }

                if (showTimePicker) {
                    TimePickerDialog(
                        onDismiss = { onDismiss() },
                        onConfirm = {
                            datePickerState.selectedDateMillis?.let { dateMillis ->
                                onDateTimeConfirmed(
                                    dateMillis,
                                    timePickerState.hour,
                                    timePickerState.minute
                                )
                            }

                            showTimePicker = false
                            showDatePicker = false
                        }
                    ) {
                        TimeInput(
                            state = timePickerState,
                        )
                    }
                }

                if (showFlagChoose) {
                    FlagChoose(
                        onConfirm = { priority ->
                            priorityTodo = priority
                            showFlagChoose = false
                        },
                        onDismiss = { showFlagChoose = false }
                    )
                }

                if (showCategoryChoose) {
                    CategoryChooseAlert(
                        onDismiss = {
                            showCategoryChoose =  false
                        },
                        onCategorySelected = { category ->
                            categoryTodo = category.name
                        },
                        onCategoryConfirmed = { category ->
                            categoryTodo = category.name
                        }
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddTodoDialogPreview() {
    AddTodoDialog(
        onDismiss = {},
        onAddTodo = { _, _, _, _, _-> },
        onDateTimeConfirmed = { _, _, _ -> },
        scheduledTimestamp = null,
        onNavigateToCreateCategory = {},
        onCategorySelected = {},
        onShowCategoryChooseChange = {}
    )
}