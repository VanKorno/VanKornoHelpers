package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.convenience.moveToFirst
import com.vankorno.vankornohelpers.convenience.moveToLast
import com.vankorno.vankornohelpers.convenience.swapWithFirst
import com.vankorno.vankornohelpers.convenience.swapWithLast
import com.vankorno.vankornohelpers.convenience.swapWithNext
import com.vankorno.vankornohelpers.convenience.swapWithPrev
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class ReorderTest {
    
    @Test
    fun `List swap - valid index swaps correctly`() {
        val original = listOf(1, 2, 3, 4)
        val result = original.swapWithNext(1)
        assertEquals(listOf(1, 3, 2, 4), result)
    }
    
    @Test
    fun `List swap - index at end does nothing`() {
        val original = listOf(1, 2, 3)
        val result = original.swapWithNext(2)
        assertSame(original, result)
    }
    
    @Test
    fun `List swap - negative index does nothing`() {
        val original = listOf("a", "b", "c")
        val result = original.swapWithNext(-1)
        assertSame(original, result)
    }
    
    @Test
    fun `ArrayList swap - valid index swaps in place`() {
        val list = arrayListOf("x", "y", "z")
        list.swapWithNext(0)
        assertEquals(listOf("y", "x", "z"), list)
    }
    
    @Test
    fun `ArrayList swap - invalid index leaves list unchanged`() {
        val list = arrayListOf(10, 20, 30)
        list.swapWithNext(5)
        assertEquals(listOf(10, 20, 30), list)
    }
    
    @Test
    fun `ArrayList swap - last index does nothing`() {
        val list = arrayListOf(1, 2)
        list.swapWithNext(1)
        assertEquals(listOf(1, 2), list)
    }
    
    
    @Test
    fun `List swapWithPrev swaps correctly for valid index`() {
        val original = listOf("a", "b", "c", "d")
        val swapped = original.swapWithPrev(2)
        assertEquals(listOf("a", "c", "b", "d"), swapped)
    }

    @Test
    fun `List swapWithPrev with invalid index returns original list`() {
        val original = listOf(1, 2, 3)
        val swapped = original.swapWithPrev(0) // no previous element
        assertSame(original, swapped)
    }

    @Test
    fun `ArrayList swapWithPrev swaps correctly for valid index`() {
        val list = arrayListOf(10, 20, 30)
        list.swapWithPrev(2)
        assertEquals(listOf(10, 30, 20), list)
    }

    @Test
    fun `ArrayList swapWithPrev with invalid index leaves list unchanged`() {
        val list = arrayListOf("x", "y")
        list.swapWithPrev(0)
        assertEquals(listOf("x", "y"), list)
    }
    
    
    @Test
    fun `List swapWithFirst swaps element with first`() {
        val original = listOf("a", "b", "c", "d")
        val swapped = original.swapWithFirst(2)
        assertEquals(listOf("c", "b", "a", "d"), swapped)
    }

    @Test
    fun `List swapWithFirst with index 0 returns original list`() {
        val original = listOf(1, 2, 3)
        val swapped = original.swapWithFirst(0)
        assertEquals(original, swapped)
    }

    @Test
    fun `List swapWithLast swaps element with last`() {
        val original = listOf("x", "y", "z")
        val swapped = original.swapWithLast(0)
        assertEquals(listOf("z", "y", "x"), swapped)
    }

    @Test
    fun `List swapWithLast with last index returns original list`() {
        val original = listOf(5, 6, 7)
        val swapped = original.swapWithLast(2)
        assertEquals(original, swapped)
    }

    @Test
    fun `ArrayList swapWithFirst swaps element with first in place`() {
        val list = arrayListOf("p", "q", "r")
        list.swapWithFirst(2)
        assertEquals(listOf("r", "q", "p"), list)
    }

    @Test
    fun `ArrayList swapWithFirst with index 0 does nothing`() {
        val list = arrayListOf(10, 20, 30)
        list.swapWithFirst(0)
        assertEquals(listOf(10, 20, 30), list)
    }

    @Test
    fun `ArrayList swapWithLast swaps element with last in place`() {
        val list = arrayListOf("a", "b", "c")
        list.swapWithLast(0)
        assertEquals(listOf("c", "b", "a"), list)
    }

    @Test
    fun `ArrayList swapWithLast with last index does nothing`() {
        val list = arrayListOf(1, 2, 3)
        list.swapWithLast(2)
        assertEquals(listOf(1, 2, 3), list)
    }
    
    
    
    @Test
    fun `List moveToLast with last index returns original list`() {
        val original = listOf(1, 2, 3)
        val moved = original.moveToLast(2)
        assertEquals(original, moved)
    }
    
    @Test
    fun `List moveToFirst moves middle element to start`() {
        val original = listOf("a", "b", "c", "d")
        val moved = original.moveToFirst(2)
        assertEquals(listOf("c", "a", "b", "d"), moved)
    }
    
    @Test
    fun `ArrayList moveToLast moves first element to end in place`() {
        val list = arrayListOf(9, 8, 7)
        list.moveToLast(0)
        assertEquals(listOf(8, 7, 9), list)
    }
    
    @Test
    fun `ArrayList moveToFirst with first index does nothing`() {
        val list = arrayListOf("x", "y", "z")
        list.moveToFirst(0)
        assertEquals(listOf("x", "y", "z"), list)
    }
    
    
    
    
    
    
    
    
}