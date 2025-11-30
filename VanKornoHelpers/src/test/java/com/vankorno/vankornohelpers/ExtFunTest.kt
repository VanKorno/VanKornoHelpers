package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.convenience.nextOrFirst
import org.junit.Assert.assertEquals
import org.junit.Test

class ExtFunTest : BaseUnitTest() {
    
    @Test
    fun `Int nextOrFirst within range returns next`() {
        assertEquals(1, 0.nextOrFirst(0..2))
        assertEquals(2, 1.nextOrFirst(0..2))
    }
    
    @Test
    fun `Int nextOrFirst at range end returns first`() {
        assertEquals(0, 2.nextOrFirst(0..2))
        assertEquals(0, 5.nextOrFirst(0..2)) // out of range triggers first
        assertEquals(0, (-1).nextOrFirst(0..2)) // out of range triggers first
    }
    
    enum class TestEnum { A, B, C }
    
    @Test
    fun `Enum nextOrFirst returns next enum`() {
        assertEquals(TestEnum.B, TestEnum.A.nextOrFirst())
        assertEquals(TestEnum.C, TestEnum.B.nextOrFirst())
        assertEquals(TestEnum.A, TestEnum.C.nextOrFirst()) // loops back to first
    }
    
    @Test
    fun `List nextOrFirst returns next element circularly`() {
        val list = listOf("x", "y", "z")
        assertEquals("y", list.nextOrFirst(0))
        assertEquals("z", list.nextOrFirst(1))
        assertEquals("x", list.nextOrFirst(2)) // loops back to first
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}