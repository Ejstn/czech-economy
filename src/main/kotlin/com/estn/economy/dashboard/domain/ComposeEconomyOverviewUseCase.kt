package com.estn.economy.dashboard.domain

import com.estn.economy.core.presentation.formatting.*
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType.REAL_2010_PRICES
import com.estn.economy.grossdomesticproduct.domain.FetchGrossDomesticProductUseCase
import com.estn.economy.inflation.data.InflationRateRepository
import com.estn.economy.inflation.data.InflationType
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
                                    private val fetchSalaryUseCase: FetchSalaryUseCase) {

    @Cacheable("ComposeEconomyOverviewUseCase::execute")
    fun execute(): EconomyOverview {

        val rates = exchangeRepository.findAllRatesFromLastDayWhereCodeLike(configuration.exchangeRates)
                .map { it.toDomain() }
        val ratesDate = rates.first().date

        return EconomyOverview(
                exchangeRate = ExchangeRatesOverview(date = ratesDate, rates = rates),
                firstRow = listOf(
                        getInflation(),
                        getGdp(),
                        getUnemp(),
                        getSalary()))
    }

    private fun getGdp(): Triple<String, QuarterAndYear, Percentage> {
        val latestGdp = fetchGdp.fetchPercentChangesPerQuarter(REAL_2010_PRICES).last()
        return Triple(
                "Reálný HDP",
                QuarterAndYear(latestGdp.dataPoint.quarter, latestGdp.dataPoint.year),
                latestGdp.value.percentage)
    }

    private fun getSalary(): Triple<String, QuarterAndYear, CzechCrowns> {
        val averageSalary = fetchSalaryUseCase.fetchLatest()
        return Triple(
                "Průměrná mzda",
                QuarterAndYear(averageSalary.quarter, averageSalary.year),
                averageSalary.salaryCrowns.czechCrowns
        )
    }

    private fun getUnemp(): Triple<String, MonthAndYear, Percentage> {
        val unemployment = unemploymentRepository.findFirstByOrderByYearDescMonthDesc()
        return Triple(
                "Nezaměstnanost",
                MonthAndYear(unemployment.month, unemployment.year),
                unemployment.unemploymentRatePercent.percentage
        )
    }

    private fun getInflation(): Triple<String, MonthAndYear, Percentage> {
        val inflation = inflationRepository.findFirstByTypeOrderByYearDescMonthDesc(InflationType.THIS_MONTH_VS_PREVIOUS_YEARS_MONTH)
        return Triple(
                "Meziroční inflace",
                MonthAndYear(inflation.month, inflation.year),
                inflation.valuePercent.percentage
        )
    }

}

data class EconomyOverview(val exchangeRate: ExchangeRatesOverview,
                           val firstRow: List<Triple<*,*,*>>
)

data class ExchangeRatesOverview(val date: LocalDate,
                                 val rates: Collection<ExchangeRate>)
