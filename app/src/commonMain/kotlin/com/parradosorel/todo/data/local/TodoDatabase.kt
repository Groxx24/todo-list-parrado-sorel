package com.parradosorel.todo.data.local

import androidx.room.AutoMigration
import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val TodoDatabaseFileName = "todo.db"

/**
 * The lists and their items, kept on the phone. Unlike a cache it holds the only copy, so every
 * schema change needs a migration; Room writes it from the schemas exported to `app/schemas/`.
 */
@Database(
    entities = [TodoListEntity::class, TodoItemEntity::class],
    version = 2,
    autoMigrations = [
        // Adds the items table.
        AutoMigration(from = 1, to = 2),
    ],
)
@ConstructedBy(TodoDatabaseConstructor::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoListDao(): TodoListDao
    abstract fun todoItemDao(): TodoItemDao
}

// Room generates the actual for each platform.
@Suppress("KotlinNoActualForExpect")
expect object TodoDatabaseConstructor : RoomDatabaseConstructor<TodoDatabase> {
    override fun initialize(): TodoDatabase
}

/** Finishes a platform-specific builder with the settings every platform shares. */
fun RoomDatabase.Builder<TodoDatabase>.buildTodoDatabase(): TodoDatabase = this
    .setDriver(BundledSQLiteDriver())
    .setQueryCoroutineContext(Dispatchers.IO)
    .build()
