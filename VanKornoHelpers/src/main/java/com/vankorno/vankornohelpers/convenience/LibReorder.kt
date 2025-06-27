package com.vankorno.vankornohelpers.convenience

import com.vankorno.vankornohelpers.eLog

private const val TAG = "LibReorder"

fun <T> List<T>.swapWithNext(idx: Int): List<T> = swapListElements(this, idx, idx + 1)
fun <T> List<T>.swapWithPrev(idx: Int): List<T> = swapListElements(this, idx, idx - 1)

fun <T> ArrayList<T>.swapWithNext(idx: Int) = swapArrayListElements(this, idx, idx + 1)
fun <T> ArrayList<T>.swapWithPrev(idx: Int) = swapArrayListElements(this, idx, idx - 1)



fun <T> List<T>.swapWithFirst(idx: Int): List<T> = swapListElements(this, idx, 0)
fun <T> List<T>.swapWithLast(idx: Int): List<T> = swapListElements(this, idx, lastIndex)

fun <T> ArrayList<T>.swapWithFirst(idx: Int) = swapArrayListElements(this, idx, 0)
fun <T> ArrayList<T>.swapWithLast(idx: Int) = swapArrayListElements(this, idx, lastIndex)



private fun <T> swapListElements(                                                   list: List<T>,
                                                                                    idx1: Int,
                                                                                    idx2: Int
): List<T> {
    if (idx1 !in list.indices || idx2 !in list.indices) {
        // region LOG
        eLog(TAG, "swap(): index $idx1 or $idx2 out of bounds for list of size ${list.size}")
        // endregion
        return list
    }
    val mutable = list.toMutableList()
    mutable[idx1] = mutable.set(idx2, mutable[idx1])
    return mutable
}

private fun <T> swapArrayListElements(                                          list: ArrayList<T>,
                                                                                idx1: Int,
                                                                                idx2: Int
) {
    if (idx1 !in list.indices || idx2 !in list.indices) {
        // region LOG
        eLog(TAG, "swap(): index $idx1 or $idx2 out of bounds for list of size ${list.size}")
        // endregion
        return
    }
    list[idx1] = list.set(idx2, list[idx1])
}




fun <T> List<T>.moveToFirst(idx: Int): List<T> {
    if (idx !in indices) {
        // region LOG
            eLog(TAG, "moveToFirst(): index $idx out of bounds for list of size $size")
        // endregion
        return this
    }
    if (idx == 0) return this
    val mutable = toMutableList()
    val item = mutable.removeAt(idx)
    mutable.add(0, item)
    return mutable
}

fun <T> List<T>.moveToLast(idx: Int): List<T> {
    if (idx !in indices) {
        // region LOG
            eLog(TAG, "moveToLast(): index $idx out of bounds for list of size $size")
        // endregion
        return this
    }
    if (idx == lastIndex) return this
    val mutable = toMutableList()
    val item = mutable.removeAt(idx)
    mutable.add(item)
    return mutable
}


fun <T> ArrayList<T>.moveToFirst(idx: Int) {
    if (idx !in indices) {
        // region LOG
            eLog(TAG, "moveToFirst(): index $idx out of bounds for list of size ${this.size}" )
        // endregion
        return
    }
    if (idx == 0) return
    val item = removeAt(idx)
    add(0, item)
}

fun <T> ArrayList<T>.moveToLast(idx: Int) {
    if (idx !in indices) {
        // region LOG
            eLog(TAG, "moveToLast(): index $idx out of bounds for list of size ${this.size}")
        // endregion
        return
    }
    if (idx == lastIndex) return
    val item = removeAt(idx)
    add(item)
}





