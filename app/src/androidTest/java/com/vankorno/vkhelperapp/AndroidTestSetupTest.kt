package com.vankorno.vkhelperapp

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vankorno.vankornohelpers.values.LibGlobals.androidTestsRun
import com.vankorno.vankornohelpers.values.LibGlobals.testsRun
import com.vankorno.vankornohelpers.values.LibGlobals.unitTestsRun
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AndroidTestSetupTest {
    
    @Test
    fun isAndroidTestNotUnitTest() {
        assertEquals(true, testsRun)
        assertEquals(true, androidTestsRun)
        assertEquals(false, unitTestsRun)
    }
    
}