package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.values.LibGlobals.androidTestRun

private const val TAG = "TimeProvider"
var simulatedTime = 0L


class LibTimeProvider {
    
    fun getCurrTime() = if (androidTestRun) {
                            checkSetCurr()
                            simulatedTime
                        } else {
                            System.currentTimeMillis()
                        }
    
    
    
    private fun checkSetCurr() {
        if (simulatedTime == 0L)
            simulatedTime = System.currentTimeMillis()
    }
    
    
    
}