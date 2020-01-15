package com.estn.economy.core

import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

/**
 * Written by estn on 15.01.2020.
 */
@Component
class DateFormatter(dateFormatConfiguration: DateFormatConfiguration) {

    private val frontEndFormat = SimpleDateFormat(dateFormatConfiguration.frontEndExchangeRateDateFormat)
    private val cnbApiFormat = SimpleDateFormat(dateFormatConfiguration.cnbExchangeRateApiDateFormat)

    fun formatDateForFrontEnd(date: Date): String = frontEndFormat.format(date)

    fun formatDateForCnbApi(date: Date): String = cnbApiFormat.format(date)

}