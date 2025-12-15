package com.thalesnishida.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.thalesnishida.todo.presetention.ui.todolist_details.TodoDetailsScreen

fun NavGraphBuilder.todoDetailsScreen(navController: NavController) {
    composable<TodoDetails> { backStackEntry ->
        val args = backStackEntry.toRoute<TodoDetails>()
        TodoDetailsScreen(
            onNavigateToHomeScreen = { navController.navigate(Home)},
            todoDetails = args,
        )
    }
}
