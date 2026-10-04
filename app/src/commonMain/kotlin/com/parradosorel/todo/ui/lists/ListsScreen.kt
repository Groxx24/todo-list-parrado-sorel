package com.parradosorel.todo.ui.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parradosorel.todo.di.AppContainer
import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.model.TodoList
import com.parradosorel.todo.resources.Res
import com.parradosorel.todo.resources.lists_empty_hint
import com.parradosorel.todo.resources.lists_empty_title
import com.parradosorel.todo.resources.lists_title
import com.parradosorel.todo.resources.new_list
import com.parradosorel.todo.ui.Plus
import com.parradosorel.todo.ui.icons.IconTile
import org.jetbrains.compose.resources.stringResource

@Composable
fun ListsRoute(container: AppContainer, onNewList: () -> Unit) {
    val viewModel = viewModel { ListsViewModel(container.getLists) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    ListsScreen(state, onNewList)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(state: ListsUiState, onNewList: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(stringResource(Res.string.lists_title)) }) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNewList,
                icon = { Icon(Plus, contentDescription = null) },
                text = { Text(stringResource(Res.string.new_list)) },
            )
        },
    ) { padding ->
        when {
            state.isLoading -> Unit
            state.lists.isEmpty() -> EmptyLists(Modifier.padding(padding))
            else -> LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = padding.calculateTopPadding() + 8.dp,
                    // Room for the button over the last list.
                    bottom = padding.calculateBottomPadding() + 88.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(state.lists, key = { it.id }) { ListRow(it) }
            }
        }
    }
}

@Composable
private fun ListRow(list: TodoList) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        Row(
            Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            IconTile(list.icon)
            Text(
                list.name,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun EmptyLists(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(12.dp)) {
            IconTile(ListIcon.TODO, size = 72.dp)
            Text(
                stringResource(Res.string.lists_empty_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                stringResource(Res.string.lists_empty_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}
