package com.thalesnishida.todo.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.thalesnishida.todo.core.Response
import com.thalesnishida.todo.data.mapper.toDomain
import com.thalesnishida.todo.domain.model.User
import com.thalesnishida.todo.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithGoogle(idToken: String): Result<User> = try {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        val authResult = auth.signInWithCredential(credential).await()

        val firebaseUser = authResult.user
        if (firebaseUser != null) {
            Result.success(firebaseUser.toDomain())
        } else {
            Result.failure(Exception("Firebase user is null after Google sign-in"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signUp(
        email: String,
        password: String
    ): Result<User> = try {
        val result = auth.createUserWithEmailAndPassword(email, password).await()
        val user = result.user?.toDomain()
        if (user != null) Result.success(user) else Result.failure(Exception("User is null"))
    } catch (e: Exception) {
        Result.failure(e)
    }

    override suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<AuthResult>> = flow {
        try {
            emit(Response.Loading)
            val data = auth.signInWithEmailAndPassword(email, password).await()
            emit(Response.Success(data))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Unknown error occurred"))
        }
    }

    override suspend fun isLoggedIn() = auth.currentUser != null
}