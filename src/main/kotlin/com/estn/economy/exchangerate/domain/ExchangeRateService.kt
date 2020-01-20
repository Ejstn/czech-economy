package com.estn.economy.exchangerate.domain

import com.estn.economy.exchangerate.data.cnbapi.CNBClient
import com.estn.economy.exchangerate.data.cnbapi.toDomain
import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.data.database.toDomain
import com.estn.economy.exchangerate.data.database.toEntity
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId


/**
 * Written by estn on 13.01.2020.
 */
@Service
class ExchangeRateService(private val cnbClient: CNBClient,
                          private val exchangeRepository: ExchangeRateRepository,
                          private val configuration: ExchangeRateConfiguration) {

    private val LOGGER = LoggerFactory.getLogger(CNBClient::class.java)

    fun fetchLatestExchangeRates(): Collection<ExchangeRate> {
        val latestRates = exchangeRepository.findAllRatesFromLastDay()
        latestRates.ifEmpty {
            synchroniseCurrentRate()
            exchangeRepository.findAllRatesFromLastDay()
        }
        return latestRates.map {
            it.toDomain()
        }
    }

    fun synchroniseCurrentRate() = executeSynchForDates(listOf(LocalDate.now(ZoneId.of("UTC"))))

    fun synchronizeAllExchangeRates() {
        val dates = exchangeRepository.findAllWeekdaysThatAreMissing(configuration.largeSyncStartingDate)
                // todo convert date in jpa
                .map {
                    val format: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val parse = format.parse(it)
                    LocalDate.ofInstant(parse.toInstant(), ZoneId.of("UTC"))
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