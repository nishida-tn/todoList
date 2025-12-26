package com.thalesnishida.todo.domain.usecase

import com.google.firebase.auth.AuthResult
import com.thalesnishida.todo.core.Response
import com.thalesnishida.todo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val auth: AuthRepository
) {

}