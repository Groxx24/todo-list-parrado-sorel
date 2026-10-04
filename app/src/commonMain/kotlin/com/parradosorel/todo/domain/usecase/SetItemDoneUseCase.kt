package com.parradosorel.todo.domain.usecase

import com.parradosorel.todo.domain.repository.TodoItemRepository

class SetItemDoneUseCase(private val repository: TodoItemRepository) {
    suspend operator fun invoke(itemId: String, done: Boolean) = repository.setDone(itemId, done)
}
