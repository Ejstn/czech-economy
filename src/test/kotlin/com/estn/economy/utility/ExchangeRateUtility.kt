package com.estn.economy.utility

import com.estn.economy.exchangerate.ExchangeRateService
import com.estn.economy.exchangerate.domain.ExchangeRate
import org.mockito.Mockito.`when`
import java.util.*

fun ExchangeRateService.mockLatestRates(rates: Collection<ExchangeRate>) {
    `when`(this.fetchLatestExchangeRates()).thenReturn(rates)
}

val exampleRate = ExchangeRate(date = Date(),
        currencyCode = "USD",
        currencyName = "dolar",
        amount = 1,
        exchangeRate = 25.5,
        country = "USA")
