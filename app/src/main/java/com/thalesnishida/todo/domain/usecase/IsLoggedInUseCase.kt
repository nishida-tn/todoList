package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke() = flow {
        emit(repository.isLoggedIn())
    }
}