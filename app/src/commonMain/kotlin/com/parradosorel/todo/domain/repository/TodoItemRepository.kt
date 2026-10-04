package com.parradosorel.todo.domain.repository

import com.parradosorel.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoItemRepository {
    /** The items of one list, oldest first, sent again whenever one changes. */
    fun items(listId: String): Flow<List<TodoItem>>

    suspend fun add(item: TodoItem)

    suspend fun setDone(itemId: String, done: Boolean)

    suspend fun delete(itemId: String)
}
