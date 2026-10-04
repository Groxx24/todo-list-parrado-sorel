package com.parradosorel.todo.di

import androidx.room.RoomDatabase
import com.parradosorel.todo.data.local.RoomTodoItems
import com.parradosorel.todo.data.local.RoomTodoLists
import com.parradosorel.todo.data.local.TodoDatabase
import com.parradosorel.todo.data.local.buildTodoDatabase
import com.parradosorel.todo.domain.repository.TodoItemRepository
import com.parradosorel.todo.domain.repository.TodoListRepository
import com.parradosorel.todo.domain.usecase.AddItemUseCase
import com.parradosorel.todo.domain.usecase.CreateListUseCase
import com.parradosorel.todo.domain.usecase.DeleteItemUseCase
import com.parradosorel.todo.domain.usecase.GetItemsUseCase
import com.parradosorel.todo.domain.usecase.GetListUseCase
import com.parradosorel.todo.domain.usecase.GetListsUseCase
import com.parradosorel.todo.domain.usecase.SetItemDoneUseCase
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

/** The one place dependencies are wired. Each platform creates one, with its own database builder. */
@OptIn(ExperimentalTime::class)
class AppContainer(databaseBuilder: RoomDatabase.Builder<TodoDatabase>) {

    private val database by lazy { databaseBuilder.buildTodoDatabase() }

    private val lists: TodoListRepository by lazy { RoomTodoLists(database.todoListDao()) }

    private val items: TodoItemRepository by lazy { RoomTodoItems(database.todoItemDao()) }

    val getLists by lazy { GetListsUseCase(lists) }
    val getList by lazy { GetListUseCase(lists) }
    val createList by lazy { CreateListUseCase(lists, ::now) }

    val getItems by lazy { GetItemsUseCase(items) }
    val addItem by lazy { AddItemUseCase(items, ::now) }
    val setItemDone by lazy { SetItemDoneUseCase(items) }
    val deleteItem by lazy { DeleteItemUseCase(items) }

    private fun now() = Clock.System.now().toEpochMilliseconds()
}
