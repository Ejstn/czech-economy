package com.estn.economy

import com.estn.economy.exchangerate.ExchangeRateService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Component
class DataScheduler(private val exchangeRateService: ExchangeRateService) {


    @Scheduled(fixedRateString = "\${synchronization.exchangerate.millis:60000}")
    fun synchroniseExchangeRates() = exchangeRateService.synchroniseExchangeRates()


}