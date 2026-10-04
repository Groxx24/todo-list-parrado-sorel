package com.parradosorel.todo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDao {
    @Query("SELECT * FROM lists ORDER BY createdAt")
    fun lists(): Flow<List<TodoListEntity>>

    @Query("SELECT * FROM lists WHERE id = :id")
    fun list(id: String): Flow<TodoListEntity?>

    @Insert
    suspend fun insert(list: TodoListEntity)
}
