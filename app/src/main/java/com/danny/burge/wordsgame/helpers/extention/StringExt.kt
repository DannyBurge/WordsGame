package com.danny.burge.wordsgame.helpers.extention

fun String.replaceAt(index: Int, value: String): String {
    val len = this.length
    val res = StringBuilder(this).replace(index, index + 1, value).toString()
    return if (index == len - 1) res.take(len) else res
}
