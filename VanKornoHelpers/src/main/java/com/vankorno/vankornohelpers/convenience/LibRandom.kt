package com.vankorno.vankornohelpers.convenience

import java.security.SecureRandom

fun IntRange.libRandom(): Int {
    val min = this.first
    val max = this.last - min
    return min + SecureRandom().nextInt(max + 1)
}



