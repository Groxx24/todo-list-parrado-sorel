package com.parradosorel.todo.data.local

import com.parradosorel.todo.domain.model.TodoList
import com.parradosorel.todo.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomTodoLists(private val dao: TodoListDao) : TodoListRepository {
    override fun lists(): Flow<List<TodoList>> = dao.lists().map { lists -> lists.map { it.toDomain() } }

    override suspend fun add(list: TodoList) = dao.insert(list.toEntity())
}
