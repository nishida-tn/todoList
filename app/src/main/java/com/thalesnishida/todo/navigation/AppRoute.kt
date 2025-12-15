package com.thalesnishida.todo.navigation

import android.util.Log
import androidx.navigation.NavController
import kotlinx.serialization.Serializable

@Serializable data object Home
@Serializable data class TodoDetails(val todoId: String)
@Serializable data object CreateNewCategory