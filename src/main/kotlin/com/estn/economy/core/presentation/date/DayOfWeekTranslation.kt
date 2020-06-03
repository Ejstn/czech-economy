package com.estn.economy.core.presentation.date

import java.time.DayOfWeek

val translation = hashMapOf(
        DayOfWeek.SUNDAY to "Neděle",
        DayOfWeek.MONDAY to "Pondělí",
        DayOfWeek.TUESDAY to "Úterý",
        DayOfWeek.WEDNESDAY to "Středa",
        DayOfWeek.THURSDAY to "Čtvrtek",
        DayOfWeek.FRIDAY to "Pátek",
        DayOfWeek.SATURDAY to "Sobota"
)

fun DayOfWeek.translate(isFirstCapital: Boolean = true): String {
    val result: String = translation[this] ?: throw IllegalStateException("Unknown day of week!")

    return if (isFirstCapital) {
        result
    } else {
        result.toLowerCase()
    }
}