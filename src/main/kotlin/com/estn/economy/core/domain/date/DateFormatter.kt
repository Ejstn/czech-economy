package com.estn.economy.core.domain.date

import com.estn.economy.core.domain.date.DateFormatter.cnbArad
import com.estn.economy.core.domain.date.DateFormatter.cnbExchange
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Written by estn on 15.01.2020.
 */
object DateFormatter {

    const val CNB_EXCHANGE_RATE_API_PATTERN: String = "dd.MM.yyyy"
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


