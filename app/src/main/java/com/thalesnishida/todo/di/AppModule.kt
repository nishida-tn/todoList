package com.thalesnishida.todo.di

import com.google.firebase.auth.FirebaseAuth
import com.thalesnishida.todo.core.provider.StringProvider
import com.thalesnishida.todo.core.provider.StringProviderImpl
import com.thalesnishida.todo.data.repository.FirebaseAuthRepositoryImpl
import com.thalesnishida.todo.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindStringProvider(impl: StringProviderImpl): StringProvider

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        impl: FirebaseAuthRepositoryImpl
    ): AuthRepository
}