package com.thalesnishida.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.extensions.navigateTo
import com.thalesnishida.todo.presetention.ui.todolist.TodoListScreen

fun NavGraphBuilder.todoScreen(navController: NavController) {
    composable<Todo> {
        TodoListScreen(navController = navController)
    }
}

fun NavController.navigateToTodoScreen() {
    navigateTo(Todo)
}
