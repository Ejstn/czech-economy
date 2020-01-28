package com.estn.economy.utility

import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import org.mockito.BDDMockito.given
import java.time.LocalDate

fun FetchExchangeRateUseCase.mockLatestRates(rates: Collection<ExchangeRate>) {
    given(this.fetchLatestRates()).willReturn(rates)
}

val exampleRate = ExchangeRate(date = LocalDate.now(),
        currencyCode = "USD",
        currencyName = "dolar",
        amount = 1,
        exchangeRate = 25.5,
        country = "USA")
