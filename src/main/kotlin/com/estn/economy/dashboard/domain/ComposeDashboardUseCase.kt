package com.estn.economy.dashboard.domain

import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.core.domain.date.translate
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.grossdomesticproduct.domain.GrossDomesticProductPerYear
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.domain.FetchInflationRateUseCase
import com.estn.economy.unemploymentrate.domain.FetchUnemploymentRateUseCase
import com.estn.economy.unemploymentrate.domain.UnemploymentRatePerYearAvg
import org.springframework.stereotype.Service

@Service
class ComposeDashboardUseCase(private val fetchExchangeRate: FetchExchangeRateUseCase,
                              private val fetchGdp: FetchGrossDomesticProductUseCase,
                              private val fetchUnemploymentRate: FetchUnemploymentRateUseCase,
                              private val fetchInflation: FetchInflationRateUseCase,
                              private val dateFormatter: DateFormatter) {


    fun execute(): EconomyDashboard {
        val exchangeRates = fetchExchangeRate.fetchLatestRates()
        val date = exchangeRates.first().date

        val formattedDate = " ${date.dayOfWeek.translate(false)} ${dateFormatter.formatDateForFrontEnd(date)}"
        val gdp = fetchGdp.fetchYearyGdps()
        val unemployment = fetchUnemploymentRate.fetchAllUnempRatesAveragedByYear()
        val inflation = fetchInflation.fetchAllYearlyInflationRates()

        return EconomyDashboard(
                exchangeRatesDate = formattedDate,
                exchangeRates = exchangeRates,
                yearlyGDPs = gdp,
                yearlyUnempRates = unemployment,
                yearlyInflationRates = inflation)

    }

    data class EconomyDashboard(val exchangeRatesDate: String,
                                val exchangeRates: Collection<ExchangeRate>,
                                val yearlyGDPs: Collection<GrossDomesticProductPerYear>,
                                val yearlyUnempRates: Collection<UnemploymentRatePerYearAvg>,
                                val yearlyInflationRates: Collection<InflationRateEntity>)

}