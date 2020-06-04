package com.estn.economy.core.presentation.date

import com.estn.economy.core.presentation.date.DateFormatter.cnbArad
import com.estn.economy.core.presentation.date.DateFormatter.cnbExchange
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.IsoFields

/**
 * Written by estn on 15.01.2020.
 */
object DateFormatter {

    const val CNB_ARAD_RESPONSE_FORMAT = "d.M.yyyy"

    const val CNB_EXCHANGE_RATE_API_PATTERN = "dd.MM.yyyy"
    val cnbExchange = DateTimeFormatter.ofPattern(CNB_EXCHANGE_RATE_API_PATTERN)

    const val CNB_ARAD_API_PATTERN = "yyyyMM"
    val cnbArad = DateTimeFormatter.ofPattern(CNB_ARAD_API_PATTERN)

}

fun LocalDate.formatForCnbExchangeApi(): String {
    return cnbExchange.format(this)
}

fun LocalDate.formatForCnbArad(): String {
    return cnbArad.format(this)
}

fun LocalDate.getQuarter(): Int = this.get(IsoFields.QUARTER_OF_YEAR)


