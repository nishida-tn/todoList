package com.thalesnishida.todo.presetention.ui.register.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.core.extensions.navigateTo
import com.thalesnishida.todo.navigation.SignIn
import com.thalesnishida.todo.presetention.ui.register.signIn.SignInScreen
import com.thalesnishida.todo.presetention.ui.register.signIn.SignInViewModelImpl

fun NavGraphBuilder.signInScreen(navController: NavController) {
    composable<SignIn> {
        SignInScreen(
            onNavigate = { route ->
                navController.navigateTo(route)
            },
            viewModel = hiltViewModel<SignInViewModelImpl>()
        )
    }
}

fun NavController.signInScreen() {
    navigateTo(SignIn)
}