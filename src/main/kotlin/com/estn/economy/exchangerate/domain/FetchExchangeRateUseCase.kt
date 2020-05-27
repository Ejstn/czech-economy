package com.estn.economy.exchangerate.domain

import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import org.springframework.stereotype.Service

/**
 * Written by estn on 13.01.2020.
 */
@Service
class FetchExchangeRateUseCase(private val repository: ExchangeRateRepository,
                               private val synchronizeExchangeRate: SynchronizeExchangeRateUseCase) {

    fun fetchLatestRates(): Collection<ExchangeRate> {
        val latestRates = repository.findAllRatesFromLastDay()
        latestRates.ifEmpty {
            synchronizeExchangeRate.executeForToday()
            repository.findAllRatesFromLastDay()
        }
        return latestRates.map {
            it.toDomain()
        }
    }

    fun fetchByCurrencyOrderByDate(currencyCode: String): List<ExchangeRate> {
        return repository.findExchangeRateEntityByCurrencyCodeOrderByDate(currencyCode)
                .map { it.toDomain() }
    }

}