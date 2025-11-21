package com.thalesnishida.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.thalesnishida.todo.domain.model.Todo

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String?,
    val isCompleted: Boolean,
    val createdAt: String,
    val scheduler: Long?
)

fun TodoEntity.toDomain() : Todo {
    return Todo(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        createdAt = this.createdAt,
        timestamp = this.scheduler
    )
}

fun Todo.toEntity() : TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        createdAt = this.createdAt,
        scheduler = this.timestamp
    )
}