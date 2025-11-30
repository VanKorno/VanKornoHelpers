package com.vankorno.vankornohelpers.convenience

import com.vankorno.vankornohelpers.BaseUnitTest
import org.junit.Assert.assertEquals
import org.junit.Test

private enum class TestEnum(override val code: String) : EnumWithCode {
    EeOne("1"),
    EeTwo("2"),
    EeThree("3")
}

class EnumTest : BaseUnitTest() {
    
    @Test
    fun `enumFromCode returns correct match`() {
        val result = enumFromCode<TestEnum>("2")
        assertEquals(TestEnum.EeTwo, result)
    }
    
    @Test
    fun `enumFromCode returns first when no match`() {
        val result = enumFromCode<TestEnum>("99")
        assertEquals(TestEnum.EeOne, result)
    }
    
}