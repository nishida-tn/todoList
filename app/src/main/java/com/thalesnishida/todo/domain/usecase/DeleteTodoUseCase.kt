package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoUseCase @Inject constructor(
    private val todoRepository: TodoRepository
){
    suspend operator fun invoke(todoId: String) {
        todoRepository.deleteTodo(todoId)
    }
}