package com.thalesnishida.todo.presetention.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.core.extensions.navigateTo
import com.thalesnishida.todo.navigation.Start
import com.thalesnishida.todo.presetention.ui.onboarding.start.StartScreen

fun NavGraphBuilder.startScreen(navController: NavController) {
    composable<Start> {
        StartScreen(
            onBackClick = { navController.popBackStack() },
            onLoginClick = {},
            onCreateAccountClick = { /*TODO*/ }
        )
    }
}

fun NavController.navigateToStartScreen() {
    navigateTo(Start)
}