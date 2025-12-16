package com.thalesnishida.todo.presetention.ui.todolist_details

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.thalesnishida.todo.R
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.navigation.TodoDetails
import com.thalesnishida.todo.presetention.components.TextFieldDefault

@Composable
fun TodoDetailsScreen(
    onNavigateToHomeScreen: () -> Unit,
    viewModel: TodoListDetailsViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is TodoListDetailsSideEffect.NavigateToHome -> onNavigateToHomeScreen
                is TodoListDetailsSideEffect.ShowToast -> {
                    Toast.makeText(context, context.getString(effect.resId), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState.detailsState) {
                is TodoListDetailsState.Loading -> {
                    CircularProgressIndicator()
                }
                is TodoListDetailsState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is TodoListDetailsState.Success -> {
                    TodoDetailsContent(
                        todo = state.todo,
                        onSaveClick = { title, description ->
                            viewModel.processIntent(
                                TodoDetailsIntent.UpdateTodoDetails(
                                    title = title,
                                    description = description,
                                    isCompleted = state.todo.isCompleted
                                )
                            )
                        },
                        onFinishClick = {
                            viewModel.processIntent(TodoDetailsIntent.FinishTodo(state.todo.id))
                        },
                        onBackClick = onNavigateToHomeScreen
                    )
                }
            }
        }

    }
}

@Composable
fun TodoDetailsContent(
    todo: Todo,
    onSaveClick: (String, String) -> Unit,
    onFinishClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var title by rememberSaveable { mutableStateOf(todo.title) }
    var description by rememberSaveable { mutableStateOf(todo.description ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Detalhes da tarefa", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        TextFieldDefault(
            value = title,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { title = it }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextFieldDefault(
            value = description,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { description = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Criado em: ${todo.createdAt}")

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = onBackClick) {
                Text(stringResource(R.string.back))
            }

            Button(onClick = { onSaveClick(title, description) }) {
                Text(stringResource(R.string.save))
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(onClick = onFinishClick) {
            Text("Finalizar Tarefa")
        }
    }
}