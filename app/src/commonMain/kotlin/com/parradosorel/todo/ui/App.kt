package com.parradosorel.todo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.parradosorel.todo.di.AppContainer
import com.parradosorel.todo.ui.lists.ListsRoute
import com.parradosorel.todo.ui.newlist.NewListRoute
import com.parradosorel.todo.ui.theme.OurListsTheme

/** Two screens: the lists, and the form for a new one opened from there. */
@Composable
fun App(container: AppContainer) {
    OurListsTheme {
        var newListOpen by rememberSaveable { mutableStateOf(false) }
        if (newListOpen) {
            NewListRoute(container, onClose = { newListOpen = false })
        } else {
            ListsRoute(container, onNewList = { newListOpen = true })
        }
    }
}
