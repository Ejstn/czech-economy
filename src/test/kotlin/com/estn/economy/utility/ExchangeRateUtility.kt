package com.estn.economy.utility

import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.grossdomesticproduct.domain.GrossDomesticProductPerYear
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

fun FetchGrossDomesticProductUseCase.mockGDP(gdps: List<GrossDomesticProductPerYear>) {
    given(this.fetchYearyGdps()).willReturn(gdps)
}
