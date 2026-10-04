package com.parradosorel.todo.domain.usecase

import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.model.TodoList
import com.parradosorel.todo.domain.repository.TodoListRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

const val MAX_LIST_NAME_LENGTH = 40

class CreateListUseCase(
    private val repository: TodoListRepository,
    private val now: () -> Long,
) {
    /** Saves a new list named [name] without its surrounding spaces; a blank name is refused. */
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(name: String, icon: ListIcon): TodoList {
        val trimmed = name.trim()
        require(trimmed.isNotEmpty()) { "A list needs a name" }
        require(trimmed.length <= MAX_LIST_NAME_LENGTH) { "List name is too long" }
        val list = TodoList(id = Uuid.random().toString(), name = trimmed, icon = icon, createdAt = now())
        repository.add(list)
        return list
    }
}
