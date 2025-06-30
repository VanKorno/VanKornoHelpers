package com.vankorno.vankornohelpers

import java.util.Locale

fun Boolean.toByte(): Byte = if (this) 1 else 0
fun Boolean.toInt(): Int = if (this) 1 else 0

// =============================  FORMATTING  ==============================

fun String.normalizeNewlines() = this.replace("\r\n", "\n").replace("\r", "\n")


fun Locale.toLangAndCountry() = this.displayName
                                    .replaceFirstChar {
                                        if (it.isLowerCase())
                                            it.titlecase(Locale.ROOT)
                                        else
                                            it.toString()
                                    }
                                    .toPolitCorrect()


fun String.toPolitCorrect() = this.replace(Regex("russia\\b", RegexOption.IGNORE_CASE), "terrorist state")


fun Int.secToMinSec() = String.format(Locale.getDefault(), "%d:%02d", this / 60, this % 60)


fun Int.shortenNumStr(): String = 
    if (this > -10000) {
        if (this < 10000)
            ""+this
        else if (this < 1000000)
            this.toString().dropLast(3) + "K"
        else
            this.toString().dropLast(6) + "M"
    }
    else {
        if (this > -1000000)
            this.toString().dropLast(3) + "K"
        else
            this.toString().dropLast(6) + "M"
    }



// ===================  N O   N U L L  ||  Z E R O  ===============================

fun String.toNoNullInt(                                                          default: Int = 0
) = try {
        this.toInt()
    } catch (nfe:NumberFormatException) {
        default
    }


fun String.toNoNullLong(                                                       default: Long = 0L
) = try {
        this.toLong()
    } catch (nfe:NumberFormatException) {
        default
    }


fun Int.toNoZeroStr() = if (this == 0)  ""  else  this.toString()








