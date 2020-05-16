package com.estn.economy.dashboard.domain

import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import com.estn.economy.exchangerate.domain.ExchangeRate
import org.springframework.stereotype.Service

@Service
class ComposeEconomyOverviewUseCase(private val exchangeRepository: ExchangeRateRepository,
        private val configuration: EconomyOverviewConfiguration) {

    fun execute() : EconomyOverview {

        val rates = exchangeRepository.findAllRatesFromLastDayWhereCodeLike(configuration.exchangeRates)
                .map { it.toDomain() }

        return EconomyOverview(rates)
    }


}

data class EconomyOverview (val exchangeRates: Collection<ExchangeRate>
                            )
