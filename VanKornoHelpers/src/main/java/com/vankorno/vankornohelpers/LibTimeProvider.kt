package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.values.LibGlobals.androidTestRun
import java.util.Calendar

private const val TAG = "TimeProvider"
var simulatedTime = 0L


class LibTimeProvider {
    
    fun getCurrTime() = if (androidTestRun) {
                            checkSetCurr()
                            simulatedTime
                        } else {
                            System.currentTimeMillis()
                        }
    
    
    fun getCurrCalendar(): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = getCurrTime()
        return calendar
    } 
    
    
    private fun checkSetCurr() {
        if (simulatedTime == 0L)
            simulatedTime = System.currentTimeMillis()
    }
    
    
    
}