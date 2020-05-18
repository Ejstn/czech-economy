package com.estn.economy.core.domain.date

import java.time.Month

val monthTranslation = hashMapOf(
        Month.JANUARY to "Leden",
        Month.FEBRUARY to "Únor",
        Month.MARCH to "Březen",
        Month.APRIL to "Duben",
        Month.MAY to "Květen",
        Month.JUNE to "Červen",
        Month.JULY to "Červenec",
        Month.AUGUST to "Spren",
        Month.SEPTEMBER to "Září",
        Month.OCTOBER to "Říjen",
        Month.NOVEMBER to "Listopad",
        Month.DECEMBER to "Prosinec"
)

fun Month.translate(isFirstCapital: Boolean = true): String {
    val result: String = monthTranslation[this] ?: throw IllegalStateException("Unknown day of week!")

    return if (isFirstCapital) {
        result
    } else {
        result.toLowerCase()
    }
}