package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.domain.repository.TodoRepository
import com.thalesnishida.todo.extensions.toFormatterString
import java.time.LocalDateTime
import java.util.UUID
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(title: String, description: String? = null) {
        val newTodo = Todo(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            isCompleted = false,
            createdAt = LocalDateTime.now().toFormatterString()
        )
        todoRepository.addTodo(newTodo)
    }
}