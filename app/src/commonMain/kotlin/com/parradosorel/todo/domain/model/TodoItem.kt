package com.parradosorel.todo.domain.model

/** One thing to do in the list [listId]. [id] is a random UUID, like a list's. */
data class TodoItem(
    val id: String,
    val listId: String,
    val text: String,
    val done: Boolean,
    val createdAt: Long,
)
