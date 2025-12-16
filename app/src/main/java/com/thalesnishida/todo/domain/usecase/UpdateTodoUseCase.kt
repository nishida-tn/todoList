package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(
        todoId: String,
        title: String,
        description: String? = null,
        isCompleted: Boolean? = null
    ) {
        val existingTodo = todoRepository.getTodoSnapshot(todoId)
            ?: throw IllegalArgumentException("Todo with id $todoId not found")

        val updateTodo = existingTodo.copy(
            title = title,
            description = description ?: existingTodo.description,
            isCompleted = isCompleted ?: existingTodo.isCompleted
        )

        todoRepository.updateTodo(updateTodo)
    }
}