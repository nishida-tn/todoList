package com.thalesnishida.todo.presetention.ui.todolist_details

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.thalesnishida.todo.R
import com.thalesnishida.todo.navigation.TodoDetails
import com.thalesnishida.todo.navigation.navigateToHomeScreen
import com.thalesnishida.todo.presetention.components.TextFieldDefault

@Composable
fun TodoDetailsScreen(
    navController: NavController,
    todoDetails: TodoDetails,
    viewModel: TodoListDetailsViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.processIntent(
            TodoDetailsIntent.LoadTodoDetails(
                todoId = todoDetails.todoId
            )
        )

        viewModel.sideEffects.collect { effect ->
            when (effect) {
                is TodoListDetailsSideEffect.NavigateToHome -> navController.navigateToHomeScreen()
                is TodoListDetailsSideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (!uiState.isLoading && uiState.error == null) {
            title = uiState.title
            description = uiState.description ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Detalhes da tarefa")

        TextFieldDefault(
            value = title,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            onValueChange = { newTitle -> title = newTitle }
        )

        TextFieldDefault(
            value = description,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            onValueChange = { newDescription -> description = newDescription }
        )

        Text("Data que foi criada a tarefa: ${uiState.createdAt}")

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(
                onClick = {
                    navController.navigateToHomeScreen()
                }
            ) {
                Text(stringResource(R.string.back))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    viewModel.processIntent(
                        TodoDetailsIntent.UpdateTodoDetails(
                            todoId = todoDetails.todoId,
                            title = title,
                            description = description,
                            isCompleted = uiState.isCompleted
                        )
                    )
                }
            ) {
                Text(stringResource(R.string.save))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Button(
                onClick = {
                    viewModel.processIntent(TodoDetailsIntent.FinishTodo(todoDetails.todoId))
                }
            ) {
                Text("Finalizar Tarefa")
            }
        }
    }
}