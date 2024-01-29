package com.vlsch.draggable.ext

fun <T> MutableList<T>.swap(index1 : Int, index2 : Int): MutableList<T> {
    val tmp = this[index1]
    this[index1] = this[index2]
    this[index2] = tmp
    return this
}

fun <T> MutableList<T>.rearrangeItems(fromIndex: Int, toIndex: Int): MutableList<T> {
    if (fromIndex !in 0 until size || toIndex !in 0 until size || fromIndex == toIndex) return this

    val item = this[fromIndex]
    if (fromIndex < toIndex) {
        for (i in fromIndex until toIndex) {
            this[i] = this[i + 1]
        }
    } else {
        for (i in fromIndex downTo toIndex + 1) {
            this[i] = this[i - 1]
        }
    }
    this[toIndex] = item
    return this
}
