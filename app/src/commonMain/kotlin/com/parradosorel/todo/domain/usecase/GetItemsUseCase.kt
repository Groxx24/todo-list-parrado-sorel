package com.parradosorel.todo.domain.usecase

import com.parradosorel.todo.domain.model.TodoItem
import com.parradosorel.todo.domain.repository.TodoItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetItemsUseCase(private val repository: TodoItemRepository) {
    /** The items still to do, oldest first, then the done ones, also oldest first. */
    operator fun invoke(listId: String): Flow<List<TodoItem>> =
        repository.items(listId).map { items -> items.sortedWith(compareBy({ it.done }, { it.createdAt })) }
}
