package com.estn.economy.dashboard.domain

import com.estn.economy.core.presentation.utility.mapToPairs
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.inflation.domain.FetchInflationRateUseCase
import com.estn.economy.nationalbudget.data.PublicDebtRepository
import com.estn.economy.unemploymentrate.domain.FetchUnemploymentRateUseCase
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class ComposeDashboardUseCase(private val fetchGdp: FetchGrossDomesticProductUseCase,
                              private val fetchUnemploymentRate: FetchUnemploymentRateUseCase,
                              private val fetchInflation: FetchInflationRateUseCase,
                              private val publicDebtRepository: PublicDebtRepository,
                              private val composeOverview: ComposeEconomyOverviewUseCase) {


    @Cacheable("dashboard")
    fun execute(): EconomyDashboard {

        val unemployment = fetchUnemploymentRate.fetchAllUnempRatesAveragedByYear()
        val inflation = fetchInflation.fetchAllYearlyInflationRates()
        val publicDebt = publicDebtRepository.findAll()
        val overview = composeOverview.execute()
        val gdp = fetchGdp.fetchPercentChangesPerYear(GrossDomesticProductType.REAL_2010_PRICES)

        return EconomyDashboard(
                overview = overview,
                yearlyUnempRates = unemployment.mapToPairs(),
                yearlyInflationRates = inflation.mapToPairs(),
                realGdp2010PricesPercentChange = gdp.mapToPairs(),
                publicDebt = publicDebt.mapToPairs()
        )

    }

    data class EconomyDashboard(val overview: EconomyOverview,
                                val realGdp2010PricesPercentChange: List<Pair<Any, Any>>,
                                val yearlyUnempRates: List<Pair<Any, Any>>,
                                val yearlyInflationRates: List<Pair<Any, Any>>,
                                val publicDebt: List<Pair<Any, Any>>)

}