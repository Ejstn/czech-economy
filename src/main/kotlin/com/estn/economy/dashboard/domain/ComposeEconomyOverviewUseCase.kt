package com.estn.economy.dashboard.domain

import com.estn.economy.core.domain.date.translate
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductRepository
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationRateRepository
import com.estn.economy.inflation.data.InflationType
import com.estn.economy.salary.data.database.SalaryEntity
import com.estn.economy.salary.domain.FetchSalaryUseCase
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateEntity
import com.estn.economy.unemploymentrate.data.database.UnemploymentRateRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.Month

@Service
class ComposeEconomyOverviewUseCase(private val exchangeRepository: ExchangeRateRepository,
                                    private val inflationRepository: InflationRateRepository,
                                    private val gdpRepository: GrossDomesticProductRepository,
                                    private val unemploymentRepository: UnemploymentRateRepository,
                                    private val configuration: EconomyOverviewConfiguration,
                                    private val fetchSalaryUseCase: FetchSalaryUseCase) {

    @Cacheable("ComposeEconomyOverviewUseCase::execute", unless = "#result.canBeCached == false")
    fun execute(): EconomyOverview {

        val rates = exchangeRepository.findAllRatesFromLastDayWhereCodeLike(configuration.exchangeRates)
                .map { it.toDomain() }
        val ratesDate = rates.first().date

        val inflationEntity = inflationRepository.findFirstByTypeOrderByYearDescMonthDesc(InflationType.THIS_MONTH_VS_PREVIOUS_YEARS_MONTH)
        val inflation = InflationOverview(Month.of(inflationEntity.month).translate(true), inflationEntity)

        val unemployment = unemploymentRepository.findFirstByOrderByYearDescMonthDesc()

        val averageSalary = fetchSalaryUseCase.fetchLatest()

        val latestGdp = gdpRepository.getAllByTypeEqualsOrderByYearDesc(GrossDomesticProductType.REAL_2010_PRICES)
                .take(2)
                .let {

                    val first = it.first()
                    val second = it[1]
                    LatestGdp(first.year, (first.gdpMillionsCrowns.toDouble()
                            / second.gdpMillionsCrowns * 100) - 100)
                }

        return EconomyOverview(ExchangeRatesOverview(date = ratesDate, rates = rates),
                inflation,
                latestGdp,
                UnemploymentOverview(month = "${Month.of(unemployment.month).translate()} ${unemployment.year}",
                        unemployment = unemployment),
                averageSalary)
    }

}

data class EconomyOverview(val exchangeRate: ExchangeRatesOverview,
                           val inflation: InflationOverview,
                           val latestGdp: LatestGdp,
                           val unemployment: UnemploymentOverview,
                           val averageSalary: SalaryEntity) {

    val canBeCached: Boolean
        get() = true

}

data class ExchangeRatesOverview(val date: LocalDate,
                                 val rates: Collection<ExchangeRate>)


data class LatestGdp(val year: Int,
                     val percentChange: Double)

data class InflationOverview(val month: String,
                             val inflation: InflationRateEntity)

data class UnemploymentOverview(val month: String,
                                val unemployment: UnemploymentRateEntity)