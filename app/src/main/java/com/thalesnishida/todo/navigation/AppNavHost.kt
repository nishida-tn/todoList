package com.thalesnishida.todo.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.thalesnishida.todo.core.base.AuthenticationNavigationViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    signUpViewModel: AuthenticationNavigationViewModel = hiltViewModel()
) {
    NavHost(
        navController = navController,
        startDestination = if (signUpViewModel.isLoggedInState.value) Home else SignUp
    ) {
        homeScreen(navController)
        todoDetailsScreen(navController)
        createNewCategoryScreen(navController)
        signUpScreen(navController)
        signInScreen(navController)
    }
}
