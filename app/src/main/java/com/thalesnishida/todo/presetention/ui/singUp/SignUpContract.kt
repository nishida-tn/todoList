package com.thalesnishida.todo.presetention.ui.singUp

data class SignUpUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class SignUpIntent {
    data class ChangeName(val name: String) : SignUpIntent()
    data class ChangeEmail(val email: String) : SignUpIntent()
    data class ChangePassword(val password: String) : SignUpIntent()
    data object SingUpConfirm : SignUpIntent()
}
