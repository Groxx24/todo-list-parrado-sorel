package com.parradosorel.todo.data.local

import com.parradosorel.todo.domain.model.TodoItem
import com.parradosorel.todo.domain.repository.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTodoItems(private val dao: TodoItemDao) : TodoItemRepository {
    override fun items(listId: String): Flow<List<TodoItem>> =
        dao.items(listId).map { items -> items.map { it.toDomain() } }

    override suspend fun add(item: TodoItem) = dao.insert(item.toEntity())

    override suspend fun setDone(itemId: String, done: Boolean) = dao.setDone(itemId, done)

    override suspend fun delete(itemId: String) = dao.delete(itemId)
}
