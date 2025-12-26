package com.thalesnishida.todo.presetention.ui.singUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.domain.usecase.SignUpUseCase
import com.thalesnishida.todo.navigation.SignIn
import com.thalesnishida.todo.presetention.ui.base.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    private val singUpUseCase: SignUpUseCase,
) : ViewModel(), SignUpViewModel {

    private val _uiState = MutableStateFlow(SignUpUiState())
    override val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    override val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    override fun processIntent(intent: SignUpIntent) {
        viewModelScope.launch {
            when (intent) {
                is SignUpIntent.ChangeName -> onChangeName(intent.name)
                is SignUpIntent.ChangeEmail -> onChangeEmail(intent.email)
                is SignUpIntent.ChangePassword -> onChangePassword(intent.password)
                is SignUpIntent.SingUpConfirm -> onSignUpClick()
            }
        }
    }

    private fun onChangeName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    private fun onChangeEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun onChangePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun onChangeConfirmPassword(confirmPassword: String) {
        TODO("Not yet implemented")
    }

    private fun onSignUpClick() {
        val currentUiState = _uiState.value
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            singUpUseCase(currentUiState.email, currentUiState.password)
                .onSuccess {
                    _uiState.update { it.copy(isLoading = false) }
                    _uiEvent.send(UiEvent.Navigate(SignIn))
                }
                .onFailure { error ->
                    _uiState.update { it.copy(errorMessage = error.message, isLoading = false) }
                    _uiEvent.send(UiEvent.ShowToast("Erro ao fazer login: ${error.message}"))
                }
        }
    }


}