package com.thalesnishida.todo.core.base

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thalesnishida.todo.domain.usecase.IsLoggedInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthenticationNavigationViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {

    private val _isLoggedInUseCase = mutableStateOf(false)
    val isLoggedInState = _isLoggedInUseCase

    init {
        isLoggedIn()
    }

    private fun isLoggedIn() = viewModelScope.launch {
        isLoggedInUseCase.invoke().collect {
            _isLoggedInUseCase.value = it
        }
    }

}