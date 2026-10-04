package com.parradosorel.todo.domain

import com.parradosorel.todo.FakeTodoLists
import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.domain.usecase.CreateListUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CreateListUseCaseTest {
    private val lists = FakeTodoLists()
    private val createList = CreateListUseCase(lists, now = { 42L })

    @Test
    fun `saves the trimmed name with the picked icon`() = runBlocking {
        createList("  Christmas gifts ", ListIcon.CHRISTMAS)

        val saved = lists.saved.value.single()
        assertEquals("Christmas gifts", saved.name)
        assertEquals(ListIcon.CHRISTMAS, saved.icon)
        assertEquals(42L, saved.createdAt)
    }

    @Test
    fun `each list gets its own id`() = runBlocking {
        val first = createList("Groceries", ListIcon.GROCERIES)
        val second = createList("Groceries", ListIcon.GROCERIES)
        assertNotEquals(first.id, second.id)
    }

    @Test
    fun `a blank name is refused`() = runBlocking {
        val result = runCatching { createList("   ", ListIcon.TODO) }
        assertTrue(result.isFailure)
        assertTrue(lists.saved.value.isEmpty())
    }
}
