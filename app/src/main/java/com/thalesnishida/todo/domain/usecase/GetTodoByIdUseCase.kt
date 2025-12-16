package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.repository.TodoRepository
import javax.inject.Inject

class GetTodoByIdUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(todoId: String) =
        todoRepository.getTodoById(todoId)
}