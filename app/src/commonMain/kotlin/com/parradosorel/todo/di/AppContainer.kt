package com.parradosorel.todo.di

import androidx.room.RoomDatabase
import com.parradosorel.todo.data.local.RoomTodoLists
import com.parradosorel.todo.data.local.TodoDatabase
import com.parradosorel.todo.data.local.buildTodoDatabase
import com.parradosorel.todo.domain.repository.TodoListRepository
import com.parradosorel.todo.domain.usecase.CreateListUseCase
import com.parradosorel.todo.domain.usecase.GetListsUseCase
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/** The one place dependencies are wired. Each platform creates one, with its own database builder. */
@OptIn(ExperimentalTime::class)
class AppContainer(databaseBuilder: RoomDatabase.Builder<TodoDatabase>) {

    private val database by lazy { databaseBuilder.buildTodoDatabase() }

    private val lists: TodoListRepository by lazy { RoomTodoLists(database.todoListDao()) }

    val getLists by lazy { GetListsUseCase(lists) }
    val createList by lazy { CreateListUseCase(lists, now = { Clock.System.now().toEpochMilliseconds() }) }
}
