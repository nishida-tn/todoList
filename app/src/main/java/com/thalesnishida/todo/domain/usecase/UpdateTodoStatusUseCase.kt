package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.repository.TodoRepository
import javax.inject.Inject

class UpdateTodoStatusUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    suspend operator fun invoke(todoId: String, isCompleted: Boolean) {
        val existingTodo = todoRepository.getTodoById(todoId)
            ?: throw IllegalArgumentException("Todo with id $todoId not found")

        val updatedTodo = existingTodo.copy(isCompleted = isCompleted)
        todoRepository.updateTodo(updatedTodo)
    }
}