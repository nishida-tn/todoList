package com.thalesnishida.todo.di

import com.thalesnishida.todo.data.repository.TodoRepositoryImpl
import com.thalesnishida.todo.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTodoRepository(impl: TodoRepositoryImpl): TodoRepository
}