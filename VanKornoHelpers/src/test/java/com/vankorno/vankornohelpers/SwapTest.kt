package com.vankorno.vankornohelpers

import com.vankorno.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class SwapTest : BaseUnitTest() {
    
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
    
    
}