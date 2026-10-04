package com.parradosorel.todo.ui.newlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.usecase.CreateListUseCase
import com.parradosorel.todo.domain.usecase.MAX_LIST_NAME_LENGTH
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NewListUiState(
    val name: String = "",
    val icon: ListIcon = ListIcon.TODO,
    val isSaving: Boolean = false,
    val saveFailed: Boolean = false,
) {
    val canSave get() = name.isNotBlank() && !isSaving
}

/**
 * The form for a new list. It outlives the screen (ViewModels are kept for the whole session), so
 * it empties itself once the list is saved or the screen is left, and the next new list starts blank.
 */
class NewListViewModel(private val createList: CreateListUseCase) : ViewModel() {

    private val _state = MutableStateFlow(NewListUiState())
    val state: StateFlow<NewListUiState> = _state.asStateFlow()

    fun onNameChange(name: String) {
        _state.update { it.copy(name = name.take(MAX_LIST_NAME_LENGTH), saveFailed = false) }
    }

    fun onIconPicked(icon: ListIcon) {
        _state.update { it.copy(icon = icon) }
    }

    fun onSave(onSaved: () -> Unit) {
        val current = _state.value
        if (!current.canSave) return
        _state.update { it.copy(isSaving = true, saveFailed = false) }
        viewModelScope.launch {
            try {
                createList(current.name, current.icon)
                _state.value = NewListUiState()
                onSaved()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _state.update { it.copy(isSaving = false, saveFailed = true) }
            }
        }
    }

    fun onDiscard() {
        if (!_state.value.isSaving) _state.value = NewListUiState()
    }
}
