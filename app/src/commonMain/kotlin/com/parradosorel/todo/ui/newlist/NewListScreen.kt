package com.parradosorel.todo.ui.newlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.parradosorel.todo.di.AppContainer
import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.usecase.MAX_LIST_NAME_LENGTH
import com.parradosorel.todo.resources.Res
import com.parradosorel.todo.resources.back
import com.parradosorel.todo.resources.new_list_icon
import com.parradosorel.todo.resources.new_list_name
import com.parradosorel.todo.resources.new_list_name_placeholder
import com.parradosorel.todo.resources.new_list_save
import com.parradosorel.todo.resources.new_list_save_failed
import com.parradosorel.todo.resources.new_list_title
import com.parradosorel.todo.ui.BackArrow
import com.parradosorel.todo.ui.OnBack
import com.parradosorel.todo.ui.icons.emoji
import com.parradosorel.todo.ui.icons.label
import org.jetbrains.compose.resources.stringResource

/** [onClose] is called once the list is saved, and on going back (dropping what was typed). */
@Composable
fun NewListRoute(container: AppContainer, onClose: () -> Unit) {
    val viewModel = viewModel { NewListViewModel(container.createList) }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val back = {
        viewModel.onDiscard()
        onClose()
    }
    OnBack(back)
    NewListScreen(
        state = state,
        onNameChange = viewModel::onNameChange,
        onIconPicked = viewModel::onIconPicked,
        onSave = { viewModel.onSave(onSaved = onClose) },
        onBack = back,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewListScreen(
    state: NewListUiState,
    onNameChange: (String) -> Unit,
    onIconPicked: (ListIcon) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.new_list_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(BackArrow, contentDescription = stringResource(Res.string.back))
                    }
                },
                actions = {
                    TextButton(onClick = onSave, enabled = state.canSave) {
                        Text(stringResource(Res.string.new_list_save))
                    }
                },
            )
        },
    ) { padding ->
        // Six icons a row, eight rows; the name field scrolls with them so the keyboard never hides the grid for good.
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = padding.calculateTopPadding() + 8.dp,
                bottom = padding.calculateBottomPadding() + 16.dp,
            ),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    OutlinedTextField(
                        value = state.name,
                        onValueChange = onNameChange,
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text(stringResource(Res.string.new_list_name)) },
                        placeholder = { Text(stringResource(Res.string.new_list_name_placeholder)) },
                        leadingIcon = { Text(state.icon.emoji, fontSize = 22.sp) },
                        supportingText = {
                            if (state.saveFailed) {
                                Text(stringResource(Res.string.new_list_save_failed), color = MaterialTheme.colorScheme.error)
                            } else {
                                Text("${state.name.length} / $MAX_LIST_NAME_LENGTH")
                            }
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                    )
                    Text(
                        stringResource(Res.string.new_list_icon),
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp),
                    )
                }
            }
            items(ListIcon.entries) { icon ->
                IconChoice(icon, selected = icon == state.icon, onClick = { onIconPicked(icon) })
            }
        }
    }
}

@Composable
private fun IconChoice(icon: ListIcon, selected: Boolean, onClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    val label = stringResource(icon.label)
    Surface(
        modifier = Modifier
            .aspectRatio(1f)
            .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
            .semantics { contentDescription = label },
        shape = RoundedCornerShape(12.dp),
        color = if (selected) colors.primaryContainer else colors.surfaceContainer,
        border = if (selected) BorderStroke(2.dp, colors.primary) else null,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(icon.emoji, fontSize = 24.sp, modifier = Modifier.clearAndSetSemantics {})
        }
    }
}
