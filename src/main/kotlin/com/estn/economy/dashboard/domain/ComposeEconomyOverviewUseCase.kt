package com.estn.economy.dashboard.domain

import com.estn.economy.core.presentation.formatting.*
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType.REAL_2010_PRICES
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.inflation.data.InflationRateRepository
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.nationalbudget.domain.FetchNationalBudgetUseCase
import com.estn.economy.salary.domain.FetchSalaryUseCase
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ComposeEconomyOverviewUseCase(private val exchangeRepository: ExchangeRateRepository,
                                    private val inflationRepository: InflationRateRepository,
                                    private val unemploymentRepository: UnemploymentRateRepository,
                                    private val fetchGdp: FetchGrossDomesticProductUseCase,
                                    private val configuration: EconomyOverviewConfiguration,
                                    private val fetchNationalBudget: FetchNationalBudgetUseCase,
                                    private val fetchSalaryUseCase: FetchSalaryUseCase) {

    @Cacheable("ComposeEconomyOverviewUseCase::execute")
    fun execute(): EconomyOverview {

        val rates = exchangeRepository.findAllRatesFromLastDayWhereCodeLike(configuration.exchangeRates)
                .map { it.toDomain() }
        val ratesDate = rates.first().date

        return EconomyOverview(
                exchangeRate = ExchangeRatesOverview(date = ratesDate, rates = rates),
                firstOverviewItems = listOf(
                        getInflation(),
                        getGdp(),
                        getUnemp()),
                secondOverviewItems = listOf(
                        getSalary(),
                        getPublicDebt(),
                        getBudgetBalance())
        )
    }

    private fun getBudgetBalance(): Triple<*, *, *> {
        val budgetBalance = fetchNationalBudget.fetchCurrentBudgetBalance()
        return Triple(
                "Saldo rozpočtu",
                budgetBalance.year,
                budgetBalance.millionsCrowns.millionsCzechCrowns
        )
    }

    private fun getPublicDebt(): Triple<*, *, *> {
        val publicDebt = fetchNationalBudget.findCurrentPublicDebt()
        return Triple(
                "Státní dluh",
                publicDebt.year,
                publicDebt.millionsCrowns.millionsCzechCrowns
        )
    }

    private fun getGdp(): Triple<*, *, *> {
        val latestGdp = fetchGdp.fetchPercentChangesPerQuarter(REAL_2010_PRICES).last()
        return Triple(
                "Reálný HDP",
                QuarterAndYear(latestGdp.dataPoint.quarter, latestGdp.dataPoint.year),
                latestGdp.value.percentage)
    }

    private fun getSalary(): Triple<*, *, *> {
        val averageSalary = fetchSalaryUseCase.fetchLatest()
        return Triple(
                "Průměrná mzda",
                QuarterAndYear(averageSalary.quarter, averageSalary.year),
                averageSalary.salaryCrowns.czechCrowns
        )
    }

    private fun getUnemp(): Triple<*, *, *> {
        val unemployment = unemploymentRepository.findFirstByOrderByYearDescMonthDesc()
        return Triple(
                "Nezaměstnanost",
                MonthAndYear(unemployment.month, unemployment.year),
                unemployment.unemploymentRatePercent.percentage
        )
    }

    private fun getInflation(): Triple<*, *, *> {
        val inflation = inflationRepository.findFirstByTypeOrderByYearDescMonthDesc(InflationType.THIS_YEAR_VS_LAST_YEAR)
        return Triple(
                "Inflace",
                MonthAndYear(inflation.month, inflation.year),
                inflation.valuePercent.percentage
        )
    }

}

data class EconomyOverview(val exchangeRate: ExchangeRatesOverview,
                           val firstOverviewItems: List<Triple<*, *, *>>,
                           val secondOverviewItems: List<Triple<*, *, *>>
)

data class ExchangeRatesOverview(val date: LocalDate,
                                 val rates: Collection<ExchangeRate>)
