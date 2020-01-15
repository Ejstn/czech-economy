package com.estn.economy.core

import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Written by ctn.
 */
@Component
class DateFactory {

    val millisInOneDay = TimeUnit.DAYS.toMillis(1)

    fun generateDaysGoingBackIncludingToday(numberOfDaysBack: Int): Collection<Date> {
        val currentTimestamp = System.currentTimeMillis()

        return IntRange(0, numberOfDaysBack)
                .map {
                    currentTimestamp - (it * millisInOneDay)
                }
                .map {
                    Date(it)
                }

    }

}