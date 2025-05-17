package com.vankorno.vankornohelpers

import java.security.SecureRandom
import java.util.Locale

fun IntRange.libRandom(): Int {
    val min = this.first
    val max = this.last - min
    return min + SecureRandom().nextInt(max + 1)
}


fun Int.nextOrFirst(                                                  range: IntRange = 0..1
) = if (this >= range.first  &&  this < range.last)
        this + 1
    else
        range.first


fun Boolean.toByte(): Byte = if (this) 1 else 0
fun Boolean.toInt(): Int = if (this) 1 else 0



// =============================  FORMATTING  ==============================

fun String.normalizeNewlines() = this.replace("\r\n", "\n").replace("\r", "\n")


fun String.toPolitCorrect() = this.replace(Regex("russia\\b", RegexOption.IGNORE_CASE), "moskovia")


fun Int.secToMinSec() = String.format(Locale.getDefault(), "%d:%02d", this / 60, this % 60)


fun Int.shortenNumStr(): String {
        return if (this > -10000) {
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











