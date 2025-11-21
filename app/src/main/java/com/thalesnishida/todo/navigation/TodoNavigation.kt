package com.thalesnishida.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.extensions.navigateTo
import com.thalesnishida.todo.presetention.ui.home.HomeScreen

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable<Home> {
        HomeScreen(navController = navController)
    }
}

fun NavController.navigateToHomeScreen() {
    navigateTo(Home)
}
