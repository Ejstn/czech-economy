package com.estn.economy.exchangerate

import org.springframework.stereotype.Service

/**
 * Written by estn on 13.01.2020.
 */
@Service
class ExchangeRateService (private val cnbClient: CNBClient) {

    fun fetchExchangeRate(): ExchangeRateDto = cnbClient.fetchExchangeRate()

}