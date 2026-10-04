package com.parradosorel.todo.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.parradosorel.todo.di.AppContainer
import com.parradosorel.todo.ui.list.ListRoute
import com.parradosorel.todo.ui.lists.ListsRoute
import com.parradosorel.todo.ui.newlist.NewListRoute
import com.parradosorel.todo.ui.theme.OurListsTheme

/** Three screens: the lists, and from there the form for a new one or the items of one list. */
@Composable
fun App(container: AppContainer) {
    OurListsTheme {
        var newListOpen by rememberSaveable { mutableStateOf(false) }
        var openListId by rememberSaveable { mutableStateOf<String?>(null) }
        val listId = openListId
        when {
            newListOpen -> NewListRoute(container, onClose = { newListOpen = false })
            listId != null -> ListRoute(container, listId, onBack = { openListId = null })
            else -> ListsRoute(container, onNewList = { newListOpen = true }, onOpenList = { openListId = it })
        }
    }
}
