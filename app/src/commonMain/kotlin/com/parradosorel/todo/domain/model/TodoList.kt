package com.parradosorel.todo.domain.model

/** One list, such as "Christmas gifts". [id] is a random UUID, so two phones never pick the same one. */
data class TodoList(
    val id: String,
    val name: String,
    val icon: ListIcon,
    val createdAt: Long,
)
