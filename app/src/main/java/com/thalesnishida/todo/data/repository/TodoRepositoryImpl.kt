package com.thalesnishida.todo.data.repository

import com.thalesnishida.todo.data.local.TodoDao
import com.thalesnishida.todo.data.local.toDomain
import com.thalesnishida.todo.data.local.toEntity
import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    override fun getTodos(): Flow<List<Todo>> {
        return todoDao.getTodos().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addTodo(todo: Todo) {
        todoDao.insertTodo(todo.toEntity())
    }

    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todoId: String) {
        todoDao.deleteTodoById(todoId)
    }

    override suspend fun getTodoById(todoId: String): Todo? {
        return todoDao.getTodoById(todoId)?.toDomain()
    }
}