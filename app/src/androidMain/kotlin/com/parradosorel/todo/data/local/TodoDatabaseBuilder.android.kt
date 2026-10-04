package com.parradosorel.todo.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun todoDatabaseBuilder(context: Context): RoomDatabase.Builder<TodoDatabase> {
    val appContext = context.applicationContext
    return Room.databaseBuilder<TodoDatabase>(
        context = appContext,
        name = appContext.getDatabasePath(TodoDatabaseFileName).absolutePath,
    )
}
