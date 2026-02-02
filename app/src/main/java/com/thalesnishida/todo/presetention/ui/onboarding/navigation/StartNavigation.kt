package com.thalesnishida.todo.presetention.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.core.extensions.navigateTo
import com.thalesnishida.todo.presetention.ui.onboarding.start.StartScreen
import com.thalesnishida.todo.presetention.ui.register.navigation.signInScreen
import com.thalesnishida.todo.navigation.Start
import com.thalesnishida.todo.presetention.ui.register.navigation.signUpScreen

fun NavGraphBuilder.startScreen(navController: NavController) {
    composable<Start> {
        StartScreen(
            onBackClick = { navController.popBackStack() },
            onLoginClick = { navController.signInScreen()},
            onCreateAccountClick = { navController.signUpScreen()}
        )
    }
}

fun NavController.navigateToStartScreen() {
    navigateTo(Start)
}