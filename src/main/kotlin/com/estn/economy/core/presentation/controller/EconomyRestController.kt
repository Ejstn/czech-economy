package com.estn.economy.core.presentation.controller

import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class EconomyRestController(private val fetchExchangeUseCase: FetchExchangeRateUseCase) {

    @GetMapping("/exchangerate")
    fun getExchangeRatesApi(): ExchangeRatesResponse {
        return ExchangeRatesResponse(fetchExchangeUseCase.fetchLatestRates())
    }

    data class ExchangeRatesResponse (val rates: Collection<ExchangeRate>)

}
