package com.vankorno.vankornohelpers.convenience

fun Int.nextOrFirst(                                                  range: IntRange = 0..1
) = if (this >= range.first  &&  this < range.last)
        this + 1
    else
        range.first

inline fun <reified T : Enum<T>> T.nextOrFirst(): T {
    val values = enumValues<T>()
    val nextOrdinal = (this.ordinal + 1) % values.size
    return values[nextOrdinal]
}

fun <T> List<T>.nextOrFirst(currentIndex: Int): T = this[(currentIndex + 1) % this.size]


