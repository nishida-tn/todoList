package com.thalesnishida.todo.presetention.ui.home

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.thalesnishida.todo.R
import com.thalesnishida.todo.extensions.getAlarmManager
import com.thalesnishida.todo.navigation.TodoDetails
import com.thalesnishida.todo.navigation.navigateToTodoDetailsScreen
import com.thalesnishida.todo.presetention.components.TodoItem
import com.thalesnishida.todo.utils.AlarmScheduler
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showAddTodoDialog by remember { mutableStateOf(false) }
    val alarmScheduler = remember { AlarmScheduler(context) }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (!isGranted) {
                Toast.makeText(context, "Permissão necessária para lembretes", Toast.LENGTH_LONG)
                    .show()
            }
        }
    )

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is HomeSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is HomeSideEffect.ScrollToTop -> {
                    Toast.makeText(context, "Rolando para o topo!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.my_tasks)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showAddTodoDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_task))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else if (uiState.error != null) {
                Text(
                    text = "Erro: ${uiState.error}",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            } else if (uiState.isEmpty) {
                Image(
                    painter = painterResource(R.drawable.ic_checklist),
                    contentDescription = null,
                )
                Text(
                    text = stringResource(R.string.task_not_found),
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(uiState.todos, key = { it.id }) { todo ->
                        TodoItem(
                            todo = todo,
                            onToggleStatus = { id, isCompleted ->
                                viewModel.processIntent(
                                    HomeIntent.ToggleTodoStatus(
                                        id,
                                        isCompleted
                                    )
                                )
                            },
                            onDelete = { id ->
                                viewModel.processIntent(HomeIntent.DeleteTodo(id))
                            },
                            onClick = {
                                val todoDetails = TodoDetails(todoId = todo.id)
                                navController.navigateToTodoDetailsScreen(todoDetails)
                            }
                        )
                        HorizontalDivider(
                            Modifier,
                            DividerDefaults.Thickness,
                            DividerDefaults.color
                        )
                    }
                }
            }
        }
    }

    if (showAddTodoDialog) {
        AddTodoDialog(
            onDismiss = { showAddTodoDialog = false },
            onAddTodo = { title, description, timestamp ->
                viewModel.processIntent(HomeIntent.AddTodo(title, description, timestamp))

//                if (timestamp != null && timestamp > System.currentTimeMillis()) {
//                    val alarmId = timestamp.toInt()
//
//                    alarmScheduler.scheduleAlarm(
//                        todoId = alarmId,
//                        title = title,
//                        description = description,
//                        timestamp = timestamp
//                    )
//                    Toast.makeText(context, "Lembrete agendado!", Toast.LENGTH_SHORT).show()
//                }

                showAddTodoDialog = false
            },
            onDateTimeSelected = { date, hour, minute ->

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoDialog(
    onDismiss: () -> Unit,
    onAddTodo: (String, String, Long?) -> Unit,
    onDateTimeSelected: (String?, Int?, Int?) -> Unit,
) {
    val context = LocalContext.current

    var scheduledTimestamp by remember { mutableStateOf<Long?>(null) }

    context.getAlarmManager()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    val currentTime = Calendar.getInstance()

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        content = {
            Column(
                modifier = Modifier.padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Adicionar nova tarefa", style = MaterialTheme.typography.headlineSmall)
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

                                }
                                .size(32.dp)
                        )
                        Icon(
                            tint = MaterialTheme.colorScheme.primary,
                            painter = painterResource(R.drawable.ic_flag),
                            contentDescription = "Timer Icon",
                            modifier = Modifier
                                .clickable {

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
                                    onAddTodo(title, description, scheduledTimestamp)
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
                            datePickerState.selectedDateMillis?.let { utcTimeMillis ->
                                val utcCalendar =
                                    Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"))
                                utcCalendar.timeInMillis = utcTimeMillis

                                val year = utcCalendar.get(Calendar.YEAR)
                                val month = utcCalendar.get(Calendar.MONTH)
                                val day = utcCalendar.get(Calendar.DAY_OF_MONTH)

                                val localCalendar = Calendar.getInstance()

                                localCalendar.set(
                                    year,
                                    month,
                                    day,
                                    timePickerState.hour,
                                    timePickerState.minute,
                                    0
                                )
                                localCalendar.set(Calendar.MILLISECOND, 0)

                                scheduledTimestamp = localCalendar.timeInMillis

                                val dateString = java.text.SimpleDateFormat(
                                    "dd/MM/yyyy HH:mm:ss",
                                    java.util.Locale.getDefault()
                                )
                                    .format(localCalendar.time)
                                println("Data Agendada: $dateString | Timestamp: $scheduledTimestamp | Agora: ${System.currentTimeMillis()}")
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
            }
        }
    )
}

@Composable
fun TimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}
