package com.parradosorel.todo.domain.repository

import com.parradosorel.todo.domain.model.TodoList
import kotlinx.coroutines.flow.Flow

interface TodoListRepository {
    /** Every list, oldest first, sent again whenever one is added. */
    fun lists(): Flow<List<TodoList>>

    /** One list, or null once it is gone. */
    fun list(id: String): Flow<TodoList?>

    suspend fun add(list: TodoList)
}
