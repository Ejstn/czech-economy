package com.estn.economy.utility

import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.ExchangeRateService
import org.mockito.BDDMockito.given
import java.time.LocalDate

fun ExchangeRateService.mockLatestRates(rates: Collection<ExchangeRate>) {
    given(this.fetchLatestExchangeRates()).willReturn(rates)
}

val exampleRate = ExchangeRate(date = LocalDate.now(),
        currencyCode = "USD",
        currencyName = "dolar",
        amount = 1,
        exchangeRate = 25.5,
        country = "USA")
