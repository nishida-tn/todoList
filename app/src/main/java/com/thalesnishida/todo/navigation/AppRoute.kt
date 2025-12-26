package com.thalesnishida.todo.navigation

import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable
abstract class Route {
    fun navigate(navController: NavController) {
        when (this) {
            is Home -> navController.homeScreen()
            is TodoDetails -> navController.todoScreenDetails(this.todoId)
            is SignUp -> navController.signUpScreen()
            is SignIn -> navController.signInScreen()
            is CreateNewCategory -> navController.createNewCategoryScreen()
        }
    }
}

@Serializable
data object Home : Route()

@Serializable
data class TodoDetails(val todoId: String) : Route()

@Serializable
data object CreateNewCategory : Route()

@Serializable
data object SignUp : Route()

@Serializable
data object SignIn : Route()