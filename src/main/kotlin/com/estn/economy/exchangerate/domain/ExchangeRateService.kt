package com.estn.economy.exchangerate.domain

import com.estn.economy.core.domain.date.DateFactory
import com.estn.economy.exchangerate.data.cnbapi.CNBClient
import com.estn.economy.exchangerate.data.cnbapi.toDomain
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import com.estn.economy.exchangerate.data.database.toEntity
import org.springframework.stereotype.Service

/**
 * Written by estn on 13.01.2020.
 */
@Service
class ExchangeRateService(private val cnbClient: CNBClient,
                          private val exchangeRepository: ExchangeRateRepository,
                          private val configuration: ExchangeRateConfiguration,
                          private val dateFactory: DateFactory) {

    fun fetchLatestExchangeRates(): Collection<ExchangeRate> {
        val latestRates = exchangeRepository.findAllRatesFromLastDay()
        latestRates.ifEmpty {
            synchroniseExchangeRates()
        }
        return latestRates.map {
            it.toDomain()
        }
    }

    fun synchroniseExchangeRates() {
        dateFactory
                .generateDaysGoingBackIncludingToday(configuration.batchSyncSize - 1)
                .parallelStream()
                .map { cnbClient.fetchExchangeRateForDay(it) }
                .map { it.body!!.toDomain() }
                .flatMap { it.stream() }
                .map { it.toEntity() }
                .forEach { exchangeRepository.save(it) }
    }

}