package com.thalesnishida.todo.extensions

import androidx.navigation.NavController
import com.thalesnishida.todo.navigation.Route

fun NavController.navigateTo(route: Route) {
    val currentRoute = currentBackStackEntry?.destination?.route
    navigate(route) {
        currentRoute?.let {
            popUpTo(it) {
                inclusive = true
            }
        }
    }
}
