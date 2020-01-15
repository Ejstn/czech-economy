package com.estn.economy

import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

/**
 * Written by estn on 15.01.2020.
 */
@Component
class DateFormatter {

    fun formatDate(date: Date): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(date)
    }

}