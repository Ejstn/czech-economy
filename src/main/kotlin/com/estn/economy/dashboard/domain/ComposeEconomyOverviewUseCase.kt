package com.estn.economy.dashboard.domain

import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import com.estn.economy.exchangerate.domain.ExchangeRate
import com.estn.economy.inflation.data.InflationRateEntity
import com.estn.economy.inflation.data.InflationRateRepository
import com.estn.economy.inflation.data.InflationType
import org.springframework.stereotype.Service

@Service
class ComposeEconomyOverviewUseCase(private val exchangeRepository: ExchangeRateRepository,
                                    private val inflationRepository: InflationRateRepository,
        private val configuration: EconomyOverviewConfiguration) {

    fun execute() : EconomyOverview {

        val rates = exchangeRepository.findAllRatesFromLastDayWhereCodeLike(configuration.exchangeRates)
                .map { it.toDomain() }
        val inflation = inflationRepository.findFirstByTypeOrderByYearDescMonthDesc(InflationType.THIS_MONTH_VS_PREVIOUS_YEARS_MONTH)

        return EconomyOverview(rates, inflation)
    }


}

data class EconomyOverview (val exchangeRates: Collection<ExchangeRate>,
                            val inflation: InflationRateEntity)
