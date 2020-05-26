package com.estn.economy.dashboard.domain

import com.estn.economy.nationalbudget.data.BudgetBalanceEntity
import com.estn.economy.nationalbudget.data.BudgetBalanceRepository
import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.core.domain.date.translate
import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.exchangerate.domain.FetchExchangeRateUseCase
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductEntity
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.inflation.domain.FetchInflationRateUseCase
import com.estn.economy.nationalbudget.data.PublicDebtRepository
import com.estn.economy.unemploymentrate.domain.FetchUnemploymentRateUseCase
import org.springframework.stereotype.Service

@Service
class ComposeDashboardUseCase(private val fetchExchangeRate: FetchExchangeRateUseCase,
                              private val fetchGdp: FetchGrossDomesticProductUseCase,
                              private val fetchUnemploymentRate: FetchUnemploymentRateUseCase,
                              private val fetchInflation: FetchInflationRateUseCase,
                              private val publicDebtRepository: PublicDebtRepository,
                              private val composeOverview: ComposeEconomyOverviewUseCase,
                              private val dateFormatter: DateFormatter) {


    fun execute(): EconomyDashboard {
        val exchangeRates = fetchExchangeRate.fetchLatestRates()
        val date = exchangeRates.first().date

        val formattedDate = " ${date.dayOfWeek.translate(false)} ${dateFormatter.formatDateForFrontEnd(date)}"
        val unemployment = fetchUnemploymentRate.fetchAllUnempRatesAveragedByYear()
        val inflation = fetchInflation.fetchAllYearlyInflationRates()
        val publicDebt = publicDebtRepository.findAll()
        val overview = composeOverview.execute()
        val gdp = fetchGdp.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES)

        return EconomyDashboard(
                overview = overview,
                exchangeRatesDate = formattedDate,
                exchangeRates = exchangeRates,
                yearlyUnempRates = unemployment.mapToPairs(),
                yearlyInflationRates = inflation.mapToPairs(),
                realGdp2010PricesPercentChange = gdp.mapToPairs(),
                publicDebt = publicDebt.mapToPairs()
        )

    }

    data class EconomyDashboard(val overview: EconomyOverview,
                                val exchangeRatesDate: String,
                                val exchangeRates: Collection<ExchangeRate>,
                                val realGdp2010PricesPercentChange: List<Pair<Any, Any>>,
                                val yearlyUnempRates: List<Pair<Any, Any>>,
                                val yearlyInflationRates: List<Pair<Any, Any>>,
                                val publicDebt: List<Pair<Any, Any>>)

}