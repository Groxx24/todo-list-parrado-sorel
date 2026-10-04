package com.parradosorel.todo.domain.usecase

import com.parradosorel.todo.domain.model.TodoItem
import com.parradosorel.todo.domain.repository.TodoItemRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

const val MAX_ITEM_TEXT_LENGTH = 200

class AddItemUseCase(
    private val repository: TodoItemRepository,
    private val now: () -> Long,
) {
    /** Adds [text] without its surrounding spaces to the list [listId], not done; blank text is refused. */
    @OptIn(ExperimentalUuidApi::class)
    suspend operator fun invoke(listId: String, text: String): TodoItem {
        val trimmed = text.trim()
        require(trimmed.isNotEmpty()) { "An item needs some text" }
        require(trimmed.length <= MAX_ITEM_TEXT_LENGTH) { "Item text is too long" }
        val item = TodoItem(Uuid.random().toString(), listId, trimmed, done = false, createdAt = now())
        repository.add(item)
        return item
    }
}
