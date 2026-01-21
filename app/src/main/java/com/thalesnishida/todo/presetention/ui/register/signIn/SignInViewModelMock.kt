package com.thalesnishida.todo.presetention.ui.register.signIn

import androidx.lifecycle.ViewModel
import com.thalesnishida.todo.presetention.ui.base.UiEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class SignInViewModelMock(
    override val uiState: StateFlow<SignInUiState>,
    override val uiEvent: Flow<UiEvent>
) : ViewModel(), SignInViewModel {
    override fun processIntent(intent: SignInIntent) {
        TODO("Not yet implemented")
    }
}