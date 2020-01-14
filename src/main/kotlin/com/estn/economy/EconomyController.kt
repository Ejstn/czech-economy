package com.estn.economy

import com.estn.economy.exchangerate.ExchangeRateService
import com.estn.economy.exchangerate.domain.ExchangeRate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@RestController
class EconomyController(private val exchangeService: ExchangeRateService) {

    @GetMapping
    fun getExchangeRates(): Collection<ExchangeRate> = exchangeService.fetchExchangeRates()

}