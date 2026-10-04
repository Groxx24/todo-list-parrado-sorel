package com.parradosorel.todo.domain

import com.parradosorel.todo.FakeTodoItems
import com.parradosorel.todo.domain.model.TodoItem
import com.parradosorel.todo.domain.usecase.AddItemUseCase
import com.parradosorel.todo.domain.usecase.GetItemsUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ItemUseCasesTest {
    private val items = FakeTodoItems()
    private var clock = 0L
    private val addItem = AddItemUseCase(items, now = { clock++ })

    @Test
    fun `adds the trimmed text to the list, not done`() = runBlocking {
        addItem("list-1", "  Buy pumpkins ")

        val item = items.saved.value.single()
        assertEquals("list-1", item.listId)
        assertEquals("Buy pumpkins", item.text)
        assertFalse(item.done)
    }

    @Test
    fun `blank text is refused`() = runBlocking {
        assertTrue(runCatching { addItem("list-1", "  ") }.isFailure)
        assertTrue(items.saved.value.isEmpty())
    }

    @Test
    fun `items to do come first, then done ones, each oldest first`() = runBlocking {
        items.saved.value = listOf(
            TodoItem("a", "l", "Costume", done = true, createdAt = 1),
            TodoItem("b", "l", "Candy", done = false, createdAt = 3),
            TodoItem("c", "l", "Pumpkin", done = false, createdAt = 2),
            TodoItem("d", "l", "Lights", done = true, createdAt = 0),
            TodoItem("e", "other", "Not this list", done = false, createdAt = 0),
        )

        val shown = GetItemsUseCase(items)("l").first().map { it.id }

        assertEquals(listOf("c", "b", "d", "a"), shown)
    }
}
