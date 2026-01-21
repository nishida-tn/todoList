package com.thalesnishida.todo.presetention.ui.onboarding.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.core.extensions.navigateTo
import com.thalesnishida.todo.navigation.Onboarding
import com.thalesnishida.todo.presetention.ui.onboarding.OnboardingScreen

fun NavGraphBuilder.onBoardingScreen(navController: NavController) {
    composable<Onboarding> { backStackEntry ->
        OnboardingScreen(
            onComplete = { navController.navigateToStartScreen() },
            onSkipClick = { navController.navigateToStartScreen()}
        )
    }
}

fun NavController.navigateToOnboardingScreen() {
    navigateTo(Onboarding)
}