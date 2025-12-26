package com.thalesnishida.todo.presetention.ui.home

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thalesnishida.todo.R
import com.thalesnishida.todo.presetention.ui.home.components.AddTodoDialog
import com.thalesnishida.todo.presetention.ui.home.components.HomeDialogManager
import com.thalesnishida.todo.presetention.ui.home.components.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToDetails: (String) -> Unit,
    onNavigateToCreateCategory: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showAddTodoDialog by rememberSaveable { mutableStateOf(false) }
    var showCategoryChoose by rememberSaveable { mutableStateOf(false) }
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
                viewModel.onAddTodoClick()
            }) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_task)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (val dataState = uiState.listTodoState) {
                is ListTodoState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }

                is ListTodoState.Error -> {
                    Text(
                        text = "Erro: ${dataState.message}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                is ListTodoState.Success -> {
                    if (dataState.todos.isEmpty()) {
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
                            items(dataState.todos, key = { it.id }) { todo ->
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
                                    onClick = {
                                        viewModel.openDialog(todo.id)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    val currentTodos = (uiState.listTodoState as? ListTodoState.Success)?.todos.orEmpty()

    HomeDialogManager(
        dialogState = uiState.activeDialog,
        todos = currentTodos,
        onDismiss = { viewModel.closeDialog() },
        onSaveEdit = { id, title, desc ->
            viewModel.processIntent(HomeIntent.UpdateTodo(id, title, desc))
        },
        onAddTodo = { title, description, _, category, priority ->
            viewModel.processIntent(
                HomeIntent.AddTodo(
                    title,
                    description,
                    uiState.draftScheduledTimestamp,
                    category,
                    priority
                )
            )
            viewModel.processIntent(HomeIntent.ClearDraftTime)
        },
        draftScheduledTimestamp = uiState.draftScheduledTimestamp,
        onDateTimeConfirmed = { date, hour, minute ->
            viewModel.processIntent(HomeIntent.UpdateDraftTime(date, hour, minute))
        },
    )

}



