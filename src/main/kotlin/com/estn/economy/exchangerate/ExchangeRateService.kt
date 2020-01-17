package com.estn.economy.exchangerate

import com.estn.economy.core.DateFactory
import com.estn.economy.exchangerate.api.CNBClient
import com.estn.economy.exchangerate.api.toDomain
import com.estn.economy.exchangerate.database.ExchangeRateRepository
import com.estn.economy.exchangerate.database.toDomain
import com.estn.economy.exchangerate.database.toEntity
import com.estn.economy.exchangerate.domain.ExchangeRate
import org.springframework.stereotype.Service
import kotlin.streams.toList

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
        val exchangeRates = dateFactory
                .generateDaysGoingBackIncludingToday(configuration.batchSyncSize - 1)
                .parallelStream()
                .map {
                    cnbClient.fetchExchangeRateForDay(it)
                }
                .map {
                    it.body!!.toDomain()
                }
                .flatMap { it.stream() }
                .map { it.toEntity() }
                .toList()

        exchangeRepository.saveAll(exchangeRates)
    }

}