package com.estn.economy.core.presentation.utility

val quarters = hashMapOf(
        1 to "I",
        2 to "II",
        3 to "III",
        4 to "IV"

)

fun Int.quarterToRoman(): String {
    return quarters.get(this)
            ?: throw IllegalStateException("$this is not quarter!")
}