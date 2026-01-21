package com.thalesnishida.todo.presetention.ui.register.signIn

import com.thalesnishida.todo.presetention.ui.base.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SignInViewModel {
    val uiState: StateFlow<SignInUiState>
    val uiEvent: Flow<UiEvent>

    fun processIntent(intent: SignInIntent)
}