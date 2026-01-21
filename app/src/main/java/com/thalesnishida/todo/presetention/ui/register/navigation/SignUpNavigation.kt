package com.thalesnishida.todo.presetention.ui.register.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.core.extensions.navigateTo
import com.thalesnishida.todo.navigation.SignUp
import com.thalesnishida.todo.presetention.ui.register.singUp.SignUpScreen
import com.thalesnishida.todo.presetention.ui.register.singUp.SignUpViewModelImpl

fun NavGraphBuilder.signUpScreen(navController: NavController) {
    composable<SignUp> { backStackEntry ->
        SignUpScreen(
            onNavigate = { route ->
                navController.navigateTo(route)
            },
            viewModel = hiltViewModel<SignUpViewModelImpl>(),
        )
    }
}

fun NavController.signUpScreen() {
    navigateTo(SignUp)
}