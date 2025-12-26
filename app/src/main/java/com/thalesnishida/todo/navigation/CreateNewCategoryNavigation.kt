package com.thalesnishida.todo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.thalesnishida.todo.core.extensions.navigateTo
import com.thalesnishida.todo.presetention.ui.new_category.CreateNewCategoryScreen

fun NavGraphBuilder.createNewCategoryScreen(navController: NavController) {
    composable<CreateNewCategory> {
        CreateNewCategoryScreen(
            onCancel = {
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "open_category_dialog",
                    true
                )
                navController.popBackStack()
            },
            onCreateCategory = { name, color ->
                navController.previousBackStackEntry?.savedStateHandle?.set(
                    "open_category_dialog",
                    false
                )
                navController.popBackStack()
            }
        )
    }
}

fun NavController.createNewCategoryScreen() {
    navigateTo(CreateNewCategory)
}
