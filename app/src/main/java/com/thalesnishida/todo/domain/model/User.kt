package com.thalesnishida.todo.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String? = null,
    val isEmailVerified: Boolean = false
)
