package com.estn.economy.dashboard.domain

import com.estn.economy.nationalbudget.data.BudgetBalanceEntity
import com.estn.economy.nationalbudget.data.BudgetBalanceRepository
import com.estn.economy.core.domain.date.DateFormatter
import com.estn.economy.core.domain.date.translate
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
                              private val budgetBalanceRepository: BudgetBalanceRepository,
                              private val composeOverview: ComposeEconomyOverviewUseCase,
                              private val dateFormatter: DateFormatter) {


    fun execute(): EconomyDashboard {
        val exchangeRates = fetchExchangeRate.fetchLatestRates()
        val date = exchangeRates.first().date

        val formattedDate = " ${date.dayOfWeek.translate(false)} ${dateFormatter.formatDateForFrontEnd(date)}"
        val nominalGdp = fetchGdp.fetchGdp(GrossDomesticProductType.NOMINAL)
        val realGdp = fetchGdp.fetchGdp(GrossDomesticProductType.REAL_2010_PRICES)
        val unemployment = fetchUnemploymentRate.fetchAllUnempRatesAveragedByYear()
        val inflation = fetchInflation.fetchAllYearlyInflationRates()
        val publicDebt = publicDebtRepository.findAll()
        val budgetBalance = budgetBalanceRepository.findAll()
        val overview = composeOverview.execute()

        return EconomyDashboard(
                overview = overview,
                exchangeRatesDate = formattedDate,
                exchangeRates = exchangeRates,
                nominalGdp = nominalGdp,
                realGdp2010Prices = realGdp.map {
                    Pair(it.year, it.gdpMillionsCrowns)
                },
                yearlyUnempRates = unemployment.map {
                    Pair(it.year, it.unemploymentRatePercent)
                },
                yearlyInflationRates = inflation.map {
                    Pair(it.year, it.valuePercent)
                },
                publicDebt = publicDebt.map {
                    Pair(it.year, it.millionsCrowns)
                },
                budgetBalance = budgetBalance)

    }

    data class EconomyDashboard(val overview: EconomyOverview,
                                val exchangeRatesDate: String,
                                val exchangeRates: Collection<ExchangeRate>,
                                val nominalGdp: Collection<GrossDomesticProductEntity>,
                                val realGdp2010Prices: List<Pair<Int, Long>>,
                                val yearlyUnempRates: List<Pair<Int, Double>>,
                                val yearlyInflationRates: List<Pair<Int, Float>>,
                                val publicDebt: List<Pair<Int, Long>>,
                                val budgetBalance: Collection<BudgetBalanceEntity>)

}