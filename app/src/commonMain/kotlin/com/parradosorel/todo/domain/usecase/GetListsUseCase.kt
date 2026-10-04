package com.parradosorel.todo.domain.usecase

import com.parradosorel.todo.domain.repository.TodoListRepository

class GetListsUseCase(private val repository: TodoListRepository) {
    operator fun invoke() = repository.lists()
}
