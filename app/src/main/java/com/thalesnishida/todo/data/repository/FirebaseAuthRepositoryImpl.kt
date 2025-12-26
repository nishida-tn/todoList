package com.thalesnishida.todo.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.thalesnishida.todo.core.Response
import com.thalesnishida.todo.data.mapper.toDomain
import com.thalesnishida.todo.domain.model.User
import com.thalesnishida.todo.domain.repository.AuthRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: FirebaseAuth,
    private val storage: FirebaseStorage,
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

    override suspend fun updateProfilePicture(image: Uri): Result<Unit> = try {
        val user = auth.currentUser ?: throw Exception("No authenticated user found")

        val storageRef = storage.reference.child("images/${user.uid}/profile.jpg")

        val bitMap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(context.contentResolver, image)
        } else {
            val source = ImageDecoder.createSource(context.contentResolver, image)
            ImageDecoder.decodeBitmap(source)
        }

        val baos = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.JPEG, 25, baos)
        val data = baos.toByteArray()

        storageRef.putBytes(data).await()
        val downloadUrl = storageRef.downloadUrl.await()

        val profileUpdate = userProfileChangeRequest {
            photoUri = downloadUrl
        }
        user.updateProfile(profileUpdate).await()
        Result.success(Unit)
    } catch (e: Exception) {
        e.printStackTrace()
        Result.failure(e)
    }
}