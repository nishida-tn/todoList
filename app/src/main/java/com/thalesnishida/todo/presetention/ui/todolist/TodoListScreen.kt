package com.thalesnishida.todo.presetention.ui.todolist

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.thalesnishida.todo.extensions.navigateTo
import com.thalesnishida.todo.navigation.TodoDetails
import com.thalesnishida.todo.navigation.navigateToTodoDetailsScreen
import com.thalesnishida.todo.presetention.components.TodoItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    navController: NavController,
    viewModel: TodoListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var showAddTodoDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is TodoListSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is TodoListSideEffect.NavigateToDetail -> {
                    Toast.makeText(
                        context,
                        "Navegar para detalhes ${effect.todoId}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is TodoListSideEffect.ScrollToTop -> {
                    Toast.makeText(context, "Rolando para o topo!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Minhas Tarefas") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showAddTodoDialog = true
            }) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar Tarefa")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
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
                Text(
                    text = "Nenhuma tarefa encontrada. Adicione uma!",
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(uiState.todos, key = { it.id }) { todo ->
                        TodoItem(
                            todo = todo,
                            onToggleStatus = { id, isCompleted ->
                                viewModel.processIntent(
                                    TodoListIntent.ToggleTodoStatus(
                                        id,
                                        isCompleted
                                    )
                                )
                            },
                            onDelete = { id ->
                                viewModel.processIntent(TodoListIntent.DeleteTodo(id))
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
            onAddTodo = { title ->
                viewModel.processIntent(TodoListIntent.AddTodo(title))
                showAddTodoDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoDialog(onDismiss: () -> Unit, onAddTodo: (String) -> Unit) {
    var title by remember { mutableStateOf("") }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Adicionar Nova Tarefa") },
        text = {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título da Tarefa") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank()) {
                        onAddTodo(title)
                    }
                }
            ) {
                Text("Adicionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
