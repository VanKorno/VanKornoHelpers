package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.values.LibGlobals.androidTestRun
import com.vankorno.vankornohelpers.values.LibGlobals.simulatedTime
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

//private const val TAG = "TimeProvider"


fun getCurrTime() = if (androidTestRun) {
                        checkSetCurr()
                        simulatedTime
                    } else {
                        System.currentTimeMillis()
                    }


fun getCurrDateTime(): LocalDateTime = getCurrTime().toLocalDateTime()


private fun checkSetCurr() {
    if (simulatedTime == 0L)
        simulatedTime = System.currentTimeMillis()
}


fun dateTimeOf(m: Int = 1, d: Int = 1, h: Int = 0, min: Int = 0) = dateTimeOf(LocalDate.now().year, m, d, h, min)

fun dateTimeOf(y: Int, m: Int = 1, d: Int = 1, h: Int = 0, min: Int = 0) = LocalDateTime.of(y, m, d, h, min).toMillis()


fun LocalDateTime.toMillis(                                   zone: ZoneId = ZoneId.systemDefault()
): Long = this.atZone(zone).toInstant().toEpochMilli()


fun Long.toLocalDateTime(                                     zone: ZoneId = ZoneId.systemDefault()
): LocalDateTime = Instant.ofEpochMilli(this).atZone(zone).toLocalDateTime()