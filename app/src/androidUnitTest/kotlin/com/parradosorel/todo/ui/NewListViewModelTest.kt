package com.parradosorel.todo.ui

import com.parradosorel.todo.FakeTodoLists
import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.usecase.CreateListUseCase
import com.parradosorel.todo.domain.usecase.MAX_LIST_NAME_LENGTH
import com.parradosorel.todo.ui.newlist.NewListUiState
import com.parradosorel.todo.ui.newlist.NewListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewListViewModelTest {

    @Before
    fun setUp() = Dispatchers.setMain(UnconfinedTestDispatcher())

    @After
    fun tearDown() = Dispatchers.resetMain()

    private val lists = FakeTodoLists()
    private val viewModel = NewListViewModel(CreateListUseCase(lists, now = { 0L }))

    @Test
    fun `saving stores the list, closes the form and empties it for the next one`() {
        var closed = false
        viewModel.onNameChange("Halloween party")
        viewModel.onIconPicked(ListIcon.HALLOWEEN)

        viewModel.onSave { closed = true }

        assertTrue(closed)
        assertEquals("Halloween party", lists.saved.value.single().name)
        assertEquals(ListIcon.HALLOWEEN, lists.saved.value.single().icon)
        assertEquals(NewListUiState(), viewModel.state.value)
    }

    @Test
    fun `cannot save without a name`() {
        assertFalse(viewModel.state.value.canSave)
        viewModel.onNameChange("  ")
        assertFalse(viewModel.state.value.canSave)
    }

    @Test
    fun `a failed save keeps what was typed and says so`() {
        var closed = false
        lists.failNext = true
        viewModel.onNameChange("Dog")

        viewModel.onSave { closed = true }

        assertFalse(closed)
        assertTrue(viewModel.state.value.saveFailed)
        assertEquals("Dog", viewModel.state.value.name)
    }

    @Test
    fun `the name is cut at the maximum length`() {
        viewModel.onNameChange("x".repeat(MAX_LIST_NAME_LENGTH + 10))
        assertEquals(MAX_LIST_NAME_LENGTH, viewModel.state.value.name.length)
    }

    @Test
    fun `going back drops what was typed`() {
        viewModel.onNameChange("Vacation")
        viewModel.onDiscard()
        assertEquals(NewListUiState(), viewModel.state.value)
    }
}
