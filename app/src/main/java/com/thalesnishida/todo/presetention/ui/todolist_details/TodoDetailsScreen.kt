package com.thalesnishida.todo.presetention.ui.todolist_details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.thalesnishida.todo.navigation.TodoDetails
import com.thalesnishida.todo.navigation.navigateToTodoScreen

@Composable
fun TodoDetailsScreen(
    navController: NavController,
    todoDetails: TodoDetails,
    viewModel: TodoListDetailsViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.processIntent(TodoDetailsIntent.LoadTodoDetails(todoDetails.todoId))
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Todo List Details Screen")
        Text("ID da tarefa: ${todoDetails.todoId}")
        Text("Título da tarefa: ${uiState.title}")
        Text("Descrição da tarefa: ${uiState.description}")
        Text("Data que foi criada a tarefa: ${uiState.createdAt}")

        Button(
            onClick = {
                navController.navigateToTodoScreen()
            }
        ) {
            Text("Voltar para a lista de tarefas")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListDetailsPreview() {
    TodoDetailsScreen(
        navController = rememberNavController(),
        todoDetails = TodoDetails(todoId = "12345")
    )
}
