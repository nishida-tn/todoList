package com.thalesnishida.todo.navigation

import android.util.Log
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
abstract class Route {
    fun navigate(navController: NavController) {
        when (this) {
            Todo -> navController.navigateToTodoScreen()
            is TodoDetails -> navController.navigateToTodoDetailsScreen(this)
            None -> Log.e("Routes", "trying to navigate to empty Route")
        }
    }
}

@Serializable
data object None : Route()

@Serializable
data object Todo : Route()

@Serializable
data class TodoDetails(
    val todoId: String
) : Route()
