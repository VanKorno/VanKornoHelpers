package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.values.LibGlobals
import org.junit.Before

abstract class BaseUnitTest {
    
    @Before
    open fun setup() {
        LibGlobals.unitTestRun = true
    }
    
}