package com.parradosorel.todo.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parradosorel.todo.domain.model.TodoItem
import com.parradosorel.todo.domain.model.TodoList
import com.parradosorel.todo.domain.usecase.AddItemUseCase
import com.parradosorel.todo.domain.usecase.DeleteItemUseCase
import com.parradosorel.todo.domain.usecase.GetItemsUseCase
import com.parradosorel.todo.domain.usecase.GetListUseCase
import com.parradosorel.todo.domain.usecase.MAX_ITEM_TEXT_LENGTH
import com.parradosorel.todo.domain.usecase.SetItemDoneUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ListUiState(
    val isLoading: Boolean = true,
    /** Null while loading, and if the list is gone. */
    val list: TodoList? = null,
    val items: List<TodoItem> = emptyList(),
    /** What is typed in the new item field. */
    val draft: String = "",
    val actionFailed: Boolean = false,
) {
    val canAdd get() = draft.isNotBlank()
}

/** One list and its items, kept per list id for the whole session. */
class ListViewModel(
    private val listId: String,
    getList: GetListUseCase,
    getItems: GetItemsUseCase,
    private val addItem: AddItemUseCase,
    private val setItemDone: SetItemDoneUseCase,
    private val deleteItem: DeleteItemUseCase,
) : ViewModel() {

    private val draft = MutableStateFlow("")
    private val actionFailed = MutableStateFlow(false)

    val state: StateFlow<ListUiState> = combine(
        getList(listId),
        getItems(listId),
        draft,
        actionFailed,
    ) { list, items, draft, failed ->
        ListUiState(isLoading = false, list = list, items = items, draft = draft, actionFailed = failed)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ListUiState())

    fun onDraftChange(text: String) {
        draft.value = text.take(MAX_ITEM_TEXT_LENGTH)
        actionFailed.value = false
    }

    /** Adds what is typed and empties the field, ready for the next item. */
    fun onAdd() {
        val text = draft.value
        if (text.isBlank()) return
        draft.value = ""
        launchAction {
            try {
                addItem(listId, text)
            } catch (e: Exception) {
                // Give the text back so it is not lost.
                draft.update { it.ifEmpty { text } }
                throw e
            }
        }
    }

    fun onDoneChange(item: TodoItem, done: Boolean) = launchAction { setItemDone(item.id, done) }

    fun onDelete(item: TodoItem) = launchAction { deleteItem(item.id) }

    private fun launchAction(action: suspend () -> Unit) {
        actionFailed.value = false
        viewModelScope.launch {
            try {
                action()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                actionFailed.value = true
            }
        }
    }
}
