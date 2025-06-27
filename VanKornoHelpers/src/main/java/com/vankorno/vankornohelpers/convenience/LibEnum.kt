package com.vankorno.vankornohelpers.convenience

inline fun <reified T : Enum<T>> T.ordinalStr(): String {
    return this.ordinal.toString()
}


interface EnumWithCode { val code: String }

inline fun <reified T> enumFromCode(                                                code: String
): T
    where T : Enum<T>, T : EnumWithCode {
    return enumValues<T>().firstOrNull { it.code == code } ?: enumValues<T>().first()
}








