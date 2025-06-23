package com.vankorno.vankornohelpers

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
        eLog("LibExtFun", "swap(): index $idx1 or $idx2 out of bounds for list of size ${list.size}")
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
        eLog("LibExtFun", "swap(): index $idx1 or $idx2 out of bounds for list of size ${list.size}")
        // endregion
        return
    }
    list[idx1] = list.set(idx2, list[idx1])
}







