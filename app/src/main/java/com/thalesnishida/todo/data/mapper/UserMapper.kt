package com.thalesnishida.todo.data.mapper

import com.google.firebase.auth.FirebaseUser
import com.thalesnishida.todo.domain.model.User

fun FirebaseUser.toDomain(): User {
    return User(
        id = this.uid,
        name = this.displayName ?: this.email?.substringBefore("@") ?: "Usuário",
        email = this.email ?: "",
        photoUrl = this.photoUrl?.toString(),
        isEmailVerified = this.isEmailVerified
    )
}