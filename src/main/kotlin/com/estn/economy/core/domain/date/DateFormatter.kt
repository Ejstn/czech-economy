package com.estn.economy.core.domain.date

import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Written by estn on 15.01.2020.
 */
@Component
class DateFormatter(dateFormatConfiguration: DateFormatConfiguration) {

    private val frontEndFormat = DateTimeFormatter.ofPattern(dateFormatConfiguration.frontEndExchangeRateDateFormat)
    private val cnbApiFormat = DateTimeFormatter.ofPattern(dateFormatConfiguration.cnbExchangeRateApiDateFormat)

    fun formatDateForFrontEnd(date: LocalDate): String = frontEndFormat.format(date)
    fun formatDateForCnbApi(date: LocalDate): String = cnbApiFormat.format(date)

}