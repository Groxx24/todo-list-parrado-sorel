package com.parradosorel.todo.ui.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parradosorel.todo.domain.model.TodoList
import com.parradosorel.todo.domain.usecase.GetListsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class ListsUiState(
    val isLoading: Boolean = true,
    val lists: List<TodoList> = emptyList(),
)

class ListsViewModel(getLists: GetListsUseCase) : ViewModel() {

    val state: StateFlow<ListsUiState> = getLists()
        .map { ListsUiState(isLoading = false, lists = it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), ListsUiState())
}
