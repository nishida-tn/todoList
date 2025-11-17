package com.thalesnishida.todo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Todo) {
        todoScreen(navController)
        todoDetailsScreen(navController)
    }
}
