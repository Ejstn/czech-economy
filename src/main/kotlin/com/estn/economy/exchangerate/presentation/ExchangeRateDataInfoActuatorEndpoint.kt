package com.estn.economy.exchangerate.presentation

import com.estn.economy.exchangerate.data.database.ExchangeRateRepository
import com.estn.economy.exchangerate.domain.ExchangeRateConfiguration
import org.springframework.boot.actuate.endpoint.annotation.Endpoint
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation
import org.springframework.stereotype.Component
import java.time.LocalDate

/**
 * Written by estn on 23.01.2020.
 */
@Component
@Endpoint(id = "exchangeRateData")
class ExchangeRateDataInfoActuatorEndpoint (private val repository: ExchangeRateRepository,
                                            private val config: ExchangeRateConfiguration) {


    @ReadOperation
    fun getInfo() : ExchangeRateDataInfo {
        val totalRows = repository.count()
        val daysWithMissingRates = repository.findAllWeekdaysThatAreMissing(config.largeSyncStartingDate)
                .size

        val oldestRecordDate = repository.findFirstByOrderByDate().date

        return ExchangeRateDataInfo(totalRecords = totalRows,
                daysWithMissingRates = daysWithMissingRates,
                oldestRecordDate = oldestRecordDate)
    }


}

data class ExchangeRateDataInfo(val totalRecords: Long,
                                val daysWithMissingRates: Int,
                                val oldestRecordDate: LocalDate

                                )