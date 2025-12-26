package com.thalesnishida.todo.presetention.ui.signIn

data class SignInUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
)

sealed class SignInIntent {
    data class ChangeEmail(val email: String) : SignInIntent()
    data class ChangePassword(val password: String) : SignInIntent()
    data object SignIn : SignInIntent()
}