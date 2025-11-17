package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.domain.repository.TodoRepository
import java.util.UUID
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
){
    suspend operator fun invoke(title: String) {
        val newTodo = Todo(
            id = UUID.randomUUID().toString(),
            title = title,
            description = null,
            isCompleted = false,
            createdAt = System.currentTimeMillis()
        )
        todoRepository.addTodo(newTodo)
    }
}