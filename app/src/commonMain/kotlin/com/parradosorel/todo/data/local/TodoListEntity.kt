package com.parradosorel.todo.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.model.TodoList

@Entity(tableName = "lists")
data class TodoListEntity(
    @PrimaryKey val id: String,
    val name: String,
    /** A [ListIcon] name. */
    val icon: String,
    val createdAt: Long,
)

fun TodoListEntity.toDomain() = TodoList(id, name, ListIcon.fromName(icon), createdAt)

fun TodoList.toEntity() = TodoListEntity(id, name, icon.name, createdAt)
