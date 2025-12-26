package com.thalesnishida.todo.presetention.ui.base

import com.thalesnishida.todo.navigation.Route

sealed interface UiEvent {
    data class ShowToast(val message: String): UiEvent
    data class Navigate(val route: Route): UiEvent
}