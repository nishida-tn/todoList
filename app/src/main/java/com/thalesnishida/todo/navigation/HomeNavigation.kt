package com.thalesnishida.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.presetention.ui.home.HomeScreen

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable<Home> {
        HomeScreen(
            onNavigateToDetails = { todoId ->
                navController.navigate(TodoDetails(todoId))
            },
            onNavigateToCreateCategory ={
                navController.navigate(CreateNewCategory)
            },
        )
    }
}