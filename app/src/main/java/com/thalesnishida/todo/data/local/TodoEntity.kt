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
    val scheduler: Long?,
    val priority: Int?,
    val category: String?,
    val categoryBackgroundColor: Int?,
    val categoryColor: Int?,
    val categoryIcon: String?
)

fun TodoEntity.toDomain(): Todo {
    return Todo(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        createdAt = this.createdAt,
        scheduler = this.scheduler,
        priority = this.priority,
        category = this.category,
        categoryBackgroundColor = this.categoryBackgroundColor,
        categoryColor = this.categoryColor,
        categoryIcon = this.categoryIcon
    )
}

fun Todo.toEntity(): TodoEntity {
    return TodoEntity(
        id = this.id,
        title = this.title,
        description = this.description,
        isCompleted = this.isCompleted,
        createdAt = this.createdAt,
        scheduler = this.scheduler,
        priority = this.priority,
        category = this.category,
        categoryBackgroundColor = this.categoryBackgroundColor,
        categoryColor = this.categoryColor,
        categoryIcon = this.categoryIcon
    )
}