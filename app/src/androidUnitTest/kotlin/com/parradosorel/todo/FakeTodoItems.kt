package com.parradosorel.todo

import com.parradosorel.todo.domain.model.TodoItem
import com.parradosorel.todo.domain.repository.TodoItemRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/** Keeps items in memory; [failNext] makes the next [add] throw. */
class FakeTodoItems : TodoItemRepository {
    val saved = MutableStateFlow<List<TodoItem>>(emptyList())
    var failNext = false

    override fun items(listId: String) = saved.map { items -> items.filter { it.listId == listId } }

    override suspend fun add(item: TodoItem) {
        if (failNext) {
            failNext = false
            error("disk full")
        }
        saved.update { it + item }
    }

    override suspend fun setDone(itemId: String, done: Boolean) =
        saved.update { items -> items.map { if (it.id == itemId) it.copy(done = done) else it } }

    override suspend fun delete(itemId: String) = saved.update { items -> items.filterNot { it.id == itemId } }
}
