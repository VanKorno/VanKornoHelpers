package com.vankorno.vankornohelpers

import com.vankorno.vankornohelpers.values.LibConstants.MillisInDay
import com.vankorno.vankornohelpers.values.LibConstants.MillisInHour
import java.time.temporal.ChronoUnit


/**
 * Can return negative values.
 */
fun getDayDiff(                                                               fromMillis: Long,
                                                                                toMillis: Long,
): Int {
    val diff = toMillis - fromMillis
    return (diff / MillisInDay).toInt()
}

/**
 * Doesn't return negative values.
 */
fun getDaysTo(                                                                fromMillis: Long,
                                                                                toMillis: Long,
): Int {
    val diff = toMillis - fromMillis
    
    if (diff < MillisInDay)
        return 0
    
    return (diff / MillisInDay).toInt()
}


fun Long.getDaysTillNow(): Int = getDaysTo(this, getCurrTime())


/**
 * Can return negative values.
 */
fun getHourDiff(                                                              fromMillis: Long,
                                                                                toMillis: Long,
): Int {
    val diff = toMillis - fromMillis
    return (diff / MillisInHour).toInt()
}

/**
 * Doesn't return negative values.
 */
fun getHoursTo(                                                               fromMillis: Long,
                                                                                toMillis: Long,
): Int {
    val diff = toMillis - fromMillis
    
    if (diff < MillisInHour)
        return 0
    
    return (diff / MillisInHour).toInt()
}

fun Long.getHoursTillNow(): Int = getHoursTo(this, getCurrTime())



fun isSameDay(a: Long, b: Long): Boolean = a.toLocalDateTime().toLocalDate() == b.toLocalDateTime().toLocalDate()


fun getCalendarDayDiff(                                                       fromMillis: Long,
                                                                                toMillis: Long,
): Int {
    val from = fromMillis.toLocalDateTime().toLocalDate()
    val to = toMillis.toLocalDateTime().toLocalDate()
    return ChronoUnit.DAYS.between(from, to).toInt()
}


