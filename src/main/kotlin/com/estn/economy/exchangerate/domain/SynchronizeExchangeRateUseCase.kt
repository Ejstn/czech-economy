package com.estn.economy.exchangerate.domain

import com.estn.economy.core.data.api.CNBClient
import com.estn.economy.core.domain.getLogger
import com.estn.economy.exchangerate.data.api.ExchangeRateRootDto
import com.estn.economy.exchangerate.data.api.toDomain
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toEntity
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.streams.toList

/**
 * Written by estn on 24.01.2020.
 */
@Service
class SynchronizeExchangeRateUseCase(private val cnbClient: CNBClient,
                                     private val exchangeRepository: ExchangeRateRepository,
                                     private val configuration: ExchangeRateConfiguration) {

    private val LOGGER = getLogger(this::class.java)

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
        val syncStart = System.currentTimeMillis()
        val count = AtomicInteger(0)
        dates
                .parallelStream()
                .map {
                    try {
                        Optional.ofNullable(cnbClient.fetchExchangeRateForDay(it).body)
                    } catch (e: Exception) {
                        Optional.empty<ExchangeRateRootDto>()
                    }
                }
                .filter { it.isPresent }
                .map { it.get() }
                .peek { count.incrementAndGet() }
                .map { it.toDomain() }
                .flatMap { it.stream() }
                .map { it.toEntity() }
                .toList()
                .also { exchangeRepository.saveAll(it) }
                .also {
                    val executionTime = System.currentTimeMillis() - syncStart
                    LOGGER.info("Syncd ${count}/${dates.size} exchange rate days in ${executionTime}ms")
                }
    }
}