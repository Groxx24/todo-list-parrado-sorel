package com.parradosorel.todo.ui

import com.parradosorel.todo.FakeTodoItems
import com.parradosorel.todo.FakeTodoLists
import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.model.TodoList
import com.parradosorel.todo.domain.usecase.AddItemUseCase
import com.parradosorel.todo.domain.usecase.DeleteItemUseCase
import com.parradosorel.todo.domain.usecase.GetItemsUseCase
import com.parradosorel.todo.domain.usecase.GetListUseCase
import com.parradosorel.todo.domain.usecase.SetItemDoneUseCase
import com.parradosorel.todo.ui.list.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {

    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    private val lists = FakeTodoLists().apply {
        saved.value = listOf(TodoList("halloween", "Halloween", ListIcon.HALLOWEEN, createdAt = 0))
    }
    private val items = FakeTodoItems()
    private var clock = 0L
    // Lazy because its state starts on Dispatchers.Main, which setUp only replaces after the fields are set.
    private val viewModel by lazy {
        ListViewModel(
            listId = "halloween",
            getList = GetListUseCase(lists),
            getItems = GetItemsUseCase(items),
            addItem = AddItemUseCase(items, now = { clock++ }),
            setItemDone = SetItemDoneUseCase(items),
            deleteItem = DeleteItemUseCase(items),
        ).also {
            // The screen collects the state; without a collector it stays at its initial value.
            it.state.launchIn(TestScope(dispatcher))
        }
    }

    private val state get() = viewModel.state.value

    @Test
    fun `adding an item shows it and empties the field for the next one`() {
        viewModel.onDraftChange("Buy candy")
        viewModel.onAdd()

        assertEquals("Halloween", state.list?.name)
        assertEquals(listOf("Buy candy"), state.items.map { it.text })
        assertEquals("", state.draft)
    }

    @Test
    fun `a ticked item moves below the ones still to do`() {
        listOf("Costume", "Pumpkin").forEach {
            viewModel.onDraftChange(it)
            viewModel.onAdd()
        }

        viewModel.onDoneChange(state.items.first(), true)

        assertEquals(listOf("Pumpkin", "Costume"), state.items.map { it.text })
        assertTrue(state.items.last().done)
    }

    @Test
    fun `a deleted item is gone`() {
        viewModel.onDraftChange("Lights")
        viewModel.onAdd()

        viewModel.onDelete(state.items.single())

        assertTrue(state.items.isEmpty())
    }

    @Test
    fun `a failed add gives the text back and says so`() {
        items.failNext = true
        viewModel.onDraftChange("Spiders")

        viewModel.onAdd()

        assertEquals("Spiders", state.draft)
        assertTrue(state.actionFailed)
        assertTrue(state.items.isEmpty())
    }
}
