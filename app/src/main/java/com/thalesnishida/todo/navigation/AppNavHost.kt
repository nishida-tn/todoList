package com.thalesnishida.todo.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.thalesnishida.todo.core.base.AuthenticationNavigationViewModel
import com.thalesnishida.todo.presetention.ui.onboarding.navigation.onBoardingScreen
import com.thalesnishida.todo.presetention.ui.onboarding.navigation.startScreen
import com.thalesnishida.todo.presetention.ui.register.navigation.signInScreen
import com.thalesnishida.todo.presetention.ui.register.navigation.signUpScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    signUpViewModel: AuthenticationNavigationViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Onboarding
    ) {
        homeScreen(navController)
        todoDetailsScreen(navController)
        createNewCategoryScreen(navController)
        signUpScreen(navController)
        signInScreen(navController)
        onBoardingScreen(navController)
        startScreen(navController)
    }
}
