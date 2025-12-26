package com.thalesnishida.todo.presetention.ui.singUp

import com.thalesnishida.todo.presetention.ui.base.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SignUpViewModel {
    val uiState: StateFlow<SignUpUiState>
    val uiEvent: Flow<UiEvent>
    fun processIntent(intent: SignUpIntent)
}