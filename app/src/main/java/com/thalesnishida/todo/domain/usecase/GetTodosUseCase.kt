package com.thalesnishida.todo.domain.usecase

import com.thalesnishida.todo.domain.model.Todo
import com.thalesnishida.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val todoRepository: TodoRepository
) {
    operator fun invoke(): Flow<List<Todo>> {
        return todoRepository.getTodos()
    }
}