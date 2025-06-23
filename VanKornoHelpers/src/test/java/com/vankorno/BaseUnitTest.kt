package com.vankorno

import com.vankorno.vankornohelpers.values.LibGlobals.unitTestRun
import org.junit.Before

abstract class BaseUnitTest {
    
    @Before
    open fun setup() {
        unitTestRun = true
    }
    
}