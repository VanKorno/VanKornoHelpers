package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.values.LibConstants.MillisInDay
import com.vankorno.vankornohelpers.values.LibConstants.MillisInHour
import com.vankorno.vankornohelpers.values.LibGlobals.simulatedTime
import com.vankorno.vankornohelpers.values.LibGlobals.testsRun
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId


fun getCurrTime() = if (testsRun) {
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




fun Long.getStartOfSameDay(): Long = this.toLocalDateTime()
    .toLocalDate()
    .atStartOfDay()
    .toMillis()


fun getStartOfToday(): Long = getCurrTime().getStartOfSameDay()


fun Long.plusDays(days: Int): Long = this + days * MillisInDay
fun Long.plusHours(hours: Int): Long = this + hours * MillisInHour

fun Long.minusDays(days: Int): Long = this - days * MillisInDay
fun Long.minusHours(hours: Int): Long = this - hours * MillisInHour


fun Long.coerceNotFuture(): Long = minOf(this, getCurrTime())


fun Long.getMillisTillNow(): Long = getCurrTime() - this









