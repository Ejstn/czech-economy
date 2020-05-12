package com.estn.economy.utility

import com.estn.economy.dashboard.domain.ComposeDashboardUseCase
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductEntity
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.unemploymentrate.domain.FetchUnemploymentRateUseCase
import com.estn.economy.unemploymentrate.domain.UnemploymentRatePerYearAvg
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

fun FetchGrossDomesticProductUseCase.mockGDP(gdps: List<GrossDomesticProductEntity>) {
    given(this.fetchGdp()).willReturn(gdps)
}

fun FetchUnemploymentRateUseCase.mockUnemployment(unemployment: List<UnemploymentRatePerYearAvg>) {
    given(this.fetchAllUnempRatesAveragedByYear()).willReturn(unemployment)
}

fun ComposeDashboardUseCase.mockDashboard(dashboard: ComposeDashboardUseCase.EconomyDashboard) {
    given(this.execute()).willReturn(dashboard)
}
