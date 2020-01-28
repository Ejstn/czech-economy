package com.estn.economy.exchangerate.domain

import com.estn.economy.exchangerate.data.cnbapi.CNBClient
import com.estn.economy.exchangerate.data.cnbapi.toDomain
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Written by estn on 24.01.2020.
 */
@Service
class SynchronizeExchangeRateUseCase(private val cnbClient: CNBClient,
                                     private val exchangeRepository: ExchangeRateRepository,
                                     private val configuration: ExchangeRateConfiguration) {

    private val LOGGER = LoggerFactory.getLogger(SynchronizeExchangeRateUseCase::class.java)

    fun executeForToday() = executeSynchForDates(listOf(LocalDate.now()))

    fun executeForAllMissingDays() {
        val dates = exchangeRepository.findAllWeekdaysThatAreMissing(configuration.largeSyncStartingDate)
                // todo convert date in jpa
                .map {
                    LocalDate.parse(it, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                }
        executeSynchForDates(dates)
    }

    private fun executeSynchForDates(dates: Collection<LocalDate>) {
        dates
                .parallelStream()
                .map { cnbClient.fetchExchangeRateForDay(it) }
                .map { it.body!!.toDomain() }
                .flatMap { it.stream() }
                .map { it.toEntity() }
                .forEach { exchangeRepository.save(it) }

        LOGGER.info("Syncd a total of ${dates.size} exchange rates")
    }
}