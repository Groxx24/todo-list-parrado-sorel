package com.parradosorel.todo.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.parradosorel.todo.domain.model.TodoItem

/** Deleting a list deletes its items with it. */
@Entity(
    tableName = "items",
    foreignKeys = [
        ForeignKey(
            entity = TodoListEntity::class,
            parentColumns = ["id"],
            childColumns = ["listId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("listId")],
)
data class TodoItemEntity(
    @PrimaryKey val id: String,
    val listId: String,
    val text: String,
    val done: Boolean,
    val createdAt: Long,
)

fun TodoItemEntity.toDomain() = TodoItem(id, listId, text, done, createdAt)

fun TodoItem.toEntity() = TodoItemEntity(id, listId, text, done, createdAt)
