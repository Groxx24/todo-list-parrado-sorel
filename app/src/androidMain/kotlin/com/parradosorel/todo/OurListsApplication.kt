package com.parradosorel.todo

import android.app.Application
import com.parradosorel.todo.data.local.todoDatabaseBuilder
import com.parradosorel.todo.di.AppContainer

class OurListsApplication : Application() {
    /** Lives as long as the process, so it outlives any single activity. */
    val container: AppContainer by lazy { AppContainer(todoDatabaseBuilder(this)) }
}
