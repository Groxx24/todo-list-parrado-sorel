package com.parradosorel.todo.domain.usecase

import com.parradosorel.todo.domain.repository.TodoListRepository

class GetListUseCase(private val repository: TodoListRepository) {
    operator fun invoke(listId: String) = repository.list(listId)
}
