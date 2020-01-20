package com.estn.economy.core.domain

import com.estn.economy.exchangerate.domain.ExchangeRateService
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Profile("!test")
@Component
class DataScheduler(private val exchangeRateService: ExchangeRateService) {

    @Scheduled(fixedRateString = "\${synchronization.exchangerate.small.millis:60000}")
    fun smallRatesSync() = exchangeRateService.synchroniseCurrentRate()

    @Scheduled(fixedRateString = "\${synchronization.exchangerate.large.millis:60000}")
    fun largeRatesSync() = exchangeRateService.synchronizeAllExchangeRates()


}