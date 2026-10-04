package com.parradosorel.todo.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

const val TodoDatabaseFileName = "todo.db"

/** The lists, kept on the phone. Unlike a cache it holds the only copy, so schema changes need a migration. */
@Database(entities = [TodoListEntity::class], version = 1)
@ConstructedBy(TodoDatabaseConstructor::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoListDao(): TodoListDao
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
