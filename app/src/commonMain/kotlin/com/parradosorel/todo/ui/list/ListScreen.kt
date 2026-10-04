package com.parradosorel.todo.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parradosorel.todo.di.AppContainer
import com.parradosorel.todo.domain.model.TodoItem
import com.parradosorel.todo.resources.Res
import com.parradosorel.todo.resources.back
import com.parradosorel.todo.resources.item_add
import com.parradosorel.todo.resources.item_delete
import com.parradosorel.todo.resources.item_placeholder
import com.parradosorel.todo.resources.list_action_failed
import com.parradosorel.todo.resources.list_empty_hint
import com.parradosorel.todo.resources.list_empty_title
import com.parradosorel.todo.resources.list_progress
import com.parradosorel.todo.ui.BackArrow
import com.parradosorel.todo.ui.Close
import com.parradosorel.todo.ui.OnBack
import com.parradosorel.todo.ui.Plus
import com.parradosorel.todo.ui.icons.IconTile
import org.jetbrains.compose.resources.stringResource

@Composable
fun ListRoute(container: AppContainer, listId: String, onBack: () -> Unit) {
    val viewModel = viewModel(key = listId) {
        with(container) { ListViewModel(listId, getList, getItems, addItem, setItemDone, deleteItem) }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    OnBack(onBack)
    ListScreen(
        state = state,
        onBack = onBack,
        onDraftChange = viewModel::onDraftChange,
        onAdd = viewModel::onAdd,
        onDoneChange = viewModel::onDoneChange,
        onDelete = viewModel::onDelete,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    state: ListUiState,
    onBack: () -> Unit,
    onDraftChange: (String) -> Unit,
    onAdd: () -> Unit,
    onDoneChange: (TodoItem, Boolean) -> Unit,
    onDelete: (TodoItem) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    val list = state.list ?: return@TopAppBar
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        IconTile(list.icon, size = 36.dp)
                        Column {
                            Text(list.name, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            if (state.items.isNotEmpty()) {
                                Text(
                                    stringResource(Res.string.list_progress, state.items.count { it.done }, state.items.size),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                )
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(BackArrow, contentDescription = stringResource(Res.string.back))
                    }
                },
            )
        },
        bottomBar = { NewItemBar(state, onDraftChange, onAdd) },
    ) { padding ->
        when {
            state.isLoading -> Unit
            state.items.isEmpty() -> EmptyList(Modifier.padding(padding))
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = padding.calculateTopPadding(),
                    bottom = padding.calculateBottomPadding() + 8.dp,
                ),
            ) {
                items(state.items, key = { it.id }) { item ->
                    ItemRow(
                        item = item,
                        onDoneChange = { onDoneChange(item, it) },
                        onDelete = { onDelete(item) },
                        modifier = Modifier.animateItem(),
                    )
                }
            }
        }
    }
}

@Composable
private fun ItemRow(
    item: TodoItem,
    onDoneChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier) {
        Row(
            Modifier.fillMaxWidth().padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = item.done, onCheckedChange = onDoneChange)
            Text(
                item.text,
                modifier = Modifier.weight(1f).padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyLarge,
                color = if (item.done) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface,
                textDecoration = if (item.done) TextDecoration.LineThrough else null,
            )
            IconButton(onClick = onDelete) {
                Icon(
                    Close,
                    contentDescription = stringResource(Res.string.item_delete, item.text),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        HorizontalDivider(Modifier.padding(start = 56.dp), color = MaterialTheme.colorScheme.outlineVariant)
    }
}

/** The field for a new item, kept above the keyboard. Enter adds the item and keeps the keyboard up for the next one. */
@Composable
private fun NewItemBar(state: ListUiState, onDraftChange: (String) -> Unit, onAdd: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.surfaceContainer, tonalElevation = 3.dp) {
        Column(Modifier.navigationBarsPadding().imePadding().padding(horizontal = 16.dp, vertical = 8.dp)) {
            if (state.actionFailed) {
                Text(
                    stringResource(Res.string.list_action_failed),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = state.draft,
                    onValueChange = onDraftChange,
                    modifier = Modifier.weight(1f),
                    placeholder = { Text(stringResource(Res.string.item_placeholder)) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        imeAction = ImeAction.Done,
                    ),
                    // Not the default action, which would close the keyboard between items.
                    keyboardActions = KeyboardActions(onDone = { onAdd() }),
                    enabled = state.list != null,
                )
                FilledIconButton(onClick = onAdd, enabled = state.canAdd) {
                    Icon(Plus, contentDescription = stringResource(Res.string.item_add))
                }
            }
        }
    }
}

@Composable
private fun EmptyList(modifier: Modifier = Modifier) {
    Box(modifier.fillMaxSize().padding(32.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                stringResource(Res.string.list_empty_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
            )
            Text(
                stringResource(Res.string.list_empty_hint),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}
