package com.thalesnishida.todo.presetention.ui.register.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.core.Response
import com.thalesnishida.todo.domain.repository.AuthRepository
import com.thalesnishida.todo.navigation.Home
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
class SignInViewModelImpl @Inject constructor(
    private val repository: AuthRepository
) : SignInViewModel, ViewModel() {

    private val _uiState = MutableStateFlow(SignInUiState())
    override val uiState: StateFlow<SignInUiState> = _uiState.asStateFlow()

    private val _uiEvent = Channel<UiEvent>()
    override val uiEvent: Flow<UiEvent> = _uiEvent.receiveAsFlow()

    override fun processIntent(intent: SignInIntent) {
        viewModelScope.launch {
            when (intent) {
                is SignInIntent.ChangeEmail -> onChangeEmail(intent.email)
                is SignInIntent.ChangePassword -> onChangePassword(intent.password)
                is SignInIntent.SignIn -> onSignInClick()
            }
        }
    }

    private fun onChangeEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun onChangePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    private fun onSignInClick() {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val currentState = _uiState.value
            repository.signInWithEmailAndPassword(currentState.email, currentState.password)
                .collect {
                    when (it) {
                        is Response.Loading -> {
                            _uiState.update { state -> state.copy(isLoading = true) }
                        }

                        is Response.Success -> {
                            _uiState.update { state -> state.copy(isLoading = false) }
                            _uiEvent.send(UiEvent.Navigate(Home))
                        }

                        is Response.Error -> {
                            _uiState.update { state -> state.copy(isLoading = false) }
                            _uiEvent.send(UiEvent.ShowToast(it.message))
                        }
                    }
                }
        }
    }
}