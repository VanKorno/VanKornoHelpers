package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.values.LibGlobals.androidTestsRun
import com.vankorno.vankornohelpers.values.LibGlobals.testsRun
import com.vankorno.vankornohelpers.values.LibGlobals.unitTestsRun
import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTestSetupTest {
    @Test
    fun isUnitTestNotAndroidTest() {
        assertEquals(true, testsRun)
        assertEquals(true, unitTestsRun)
        assertEquals(false, androidTestsRun)
    }
}