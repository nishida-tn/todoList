package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.model.User
import com.thalesnishida.todo.domain.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
){
    private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()

    suspend operator fun invoke(email: String, password: String): Result<User> = withContext(
        Dispatchers.IO) {
        if(!email.matches(emailRegex)){
            return@withContext Result.failure(Exception("Invalid email format"))
        }

        if(password.length < 6){
            return@withContext Result.failure(Exception("Password must be at least 6 characters long"))
        }

        return@withContext repository.signUp(email, password)
    }
}