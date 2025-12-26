package com.thalesnishida.todo.domain.repository

import android.net.Uri
import com.google.firebase.auth.AuthResult
import com.thalesnishida.todo.core.Response
import com.thalesnishida.todo.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<User>
    suspend fun signUp(email: String, password: String): Result<User>

    suspend fun signInWithEmailAndPassword(email: String, password: String): Flow<Response<AuthResult>>

    suspend fun isLoggedIn(): Boolean

    suspend fun updateProfilePicture(image: Uri): Result<Unit>
}