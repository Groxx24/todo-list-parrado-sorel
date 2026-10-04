package com.parradosorel.todo.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM items WHERE listId = :listId ORDER BY createdAt")
    fun items(listId: String): Flow<List<TodoItemEntity>>

    @Insert
    suspend fun insert(item: TodoItemEntity)

    @Query("UPDATE items SET done = :done WHERE id = :id")
    suspend fun setDone(id: String, done: Boolean)

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun delete(id: String)
}
