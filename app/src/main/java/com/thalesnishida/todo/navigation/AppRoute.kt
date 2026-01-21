package com.thalesnishida.todo.navigation

import androidx.navigation.NavController
import com.thalesnishida.todo.presetention.ui.onboarding.navigation.navigateToOnboardingScreen
import com.thalesnishida.todo.presetention.ui.onboarding.navigation.navigateToStartScreen
import com.thalesnishida.todo.presetention.ui.register.navigation.signInScreen
import com.thalesnishida.todo.presetention.ui.register.navigation.signUpScreen
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
            is Onboarding -> navController.navigateToOnboardingScreen()
            is Start -> navController.navigateToStartScreen()
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

@Serializable
data object Onboarding: Route()

@Serializable
data object Start: Route()