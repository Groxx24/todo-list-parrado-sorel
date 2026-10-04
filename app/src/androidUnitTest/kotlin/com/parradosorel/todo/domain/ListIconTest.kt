package com.parradosorel.todo.domain

import com.parradosorel.todo.domain.model.ListIcon
import com.parradosorel.todo.ui.icons.emoji
import org.junit.Assert.assertEquals
import org.junit.Test

class ListIconTest {
    @Test
    fun `there are 48 icons, six rows of eight in the picker`() {
        assertEquals(48, ListIcon.entries.size)
    }

    @Test
    fun `every icon has its own emoji`() {
        assertEquals(ListIcon.entries.size, ListIcon.entries.map { it.emoji }.toSet().size)
    }

    @Test
    fun `an unknown saved name reads as the default icon`() {
        assertEquals(ListIcon.DOG, ListIcon.fromName("DOG"))
        assertEquals(ListIcon.TODO, ListIcon.fromName("UNICORN"))
    }
}
