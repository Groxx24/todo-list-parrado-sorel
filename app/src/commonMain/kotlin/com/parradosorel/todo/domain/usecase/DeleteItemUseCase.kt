package com.parradosorel.todo.domain.usecase

import com.parradosorel.todo.domain.repository.TodoItemRepository

class DeleteItemUseCase(private val repository: TodoItemRepository) {
    suspend operator fun invoke(itemId: String) = repository.delete(itemId)
}
