package com.parradosorel.todo

import com.parradosorel.todo.domain.model.TodoList
import com.parradosorel.todo.domain.repository.TodoListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

/** Keeps lists in memory; [failNext] makes the next [add] throw. */
class FakeTodoLists : TodoListRepository {
    val saved = MutableStateFlow<List<TodoList>>(emptyList())
    var failNext = false

    override fun lists() = saved

    override fun list(id: String) = saved.map { lists -> lists.firstOrNull { it.id == id } }

    override suspend fun add(list: TodoList) {
        if (failNext) {
            failNext = false
            error("disk full")
        }
        saved.update { it + list }
    }
}
