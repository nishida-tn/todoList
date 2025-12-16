package com.thalesnishida.todo.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY createdAt DESC")
    fun getTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :todoId")
    fun getTodoById(todoId: String): Flow<TodoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Query("DELETE FROM todos WHERE id = :todoId")
    suspend fun deleteTodoById(todoId: String)

    @Query("SELECT * FROM todos WHERE id = :todoId")
    suspend fun fetchTodoSnapshot(todoId: String): TodoEntity?
}