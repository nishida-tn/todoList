package com.thalesnishida.todo.domain.repository

import com.thalesnishida.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun addTodo(todo: Todo)
    suspend fun updateTodo(todo: Todo)
    suspend fun deleteTodo(todoId: String)
    fun getTodoById(todoId: String): Flow<Todo?>
    suspend fun getTodoSnapshot(todoId: String): Todo?
}