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
import org.springframework.stereotype.Service
import java.time.Month

@Service
class ComposeEconomyOverviewUseCase(private val exchangeRepository: ExchangeRateRepository,
                                    private val inflationRepository: InflationRateRepository,
                                    private val gdpRepository: GrossDomesticProductRepository,
                                    private val configuration: EconomyOverviewConfiguration) {

    fun execute(): EconomyOverview {

        val rates = exchangeRepository.findAllRatesFromLastDayWhereCodeLike(configuration.exchangeRates)
                .map { it.toDomain() }

        val inflationEntity = inflationRepository.findFirstByTypeOrderByYearDescMonthDesc(InflationType.THIS_MONTH_VS_PREVIOUS_YEARS_MONTH)
        val inflation = InflationOverview(Month.of(inflationEntity.month).translate(true), inflationEntity)


        val latestGdp = gdpRepository.getAllByTypeEqualsOrderByYearDesc(GrossDomesticProductType.REAL_2010_PRICES)
                .take(2)
                .let {

                    val first = it.first()
                    val second = it[1]
                    LatestGdp(first.year, (first.gdpMillionsCrowns.toDouble()
                            / second.gdpMillionsCrowns * 100) - 100)
                }

        return EconomyOverview(rates, inflation, latestGdp)
    }

}

data class EconomyOverview(val exchangeRates: Collection<ExchangeRate>,
                           val inflation: InflationOverview,
                           val latestGdp: LatestGdp)


data class LatestGdp(val year: Int,
                     val percentChange: Double)

data class InflationOverview(val month: String,
                             val inflation: InflationRateEntity)