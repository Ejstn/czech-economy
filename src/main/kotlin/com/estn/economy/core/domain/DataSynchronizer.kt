package com.estn.economy.core.domain

import com.estn.economy.exchangerate.domain.SynchronizeExchangeRateUseCase
import com.estn.economy.grossdomesticproduct.domain.SynchronizeRealGrossDomesticProduct
import com.estn.economy.salary.domain.SynchronizeSalaryUseCase
import com.estn.economy.unemploymentrate.domain.SynchronizeUnemploymentRateUseCase
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Profile("!test")
@Component
class DataSynchronizer(private val syncExchange: SynchronizeExchangeRateUseCase,
                       private val syncSalary: SynchronizeSalaryUseCase,
                       private val syncUnemployment: SynchronizeUnemploymentRateUseCase,
                       private val syncRealGdp: SynchronizeRealGrossDomesticProduct,
                       private val evictCache: EvictAllCacheUseCase) {

    companion object {
        // 1.1. 2.1. everyday at midnight
        const val EVERYDAY_AT_MIDNIGHT = "0 0 0 ? * *"
        // 1.1.2020, 1.2.2020 ... every first day of the month at midnight
        const val EVERY_FIRST_DAY_OF_MONTH = "0 0 0 1 * ?"
    }

    @ExecuteAfterStart
    @Scheduled(cron = "0 0/5 12 ? * MON-FRI") // 12:05, 12:10, 12:15 ... 12:55 everyday (cnb publishes rates at 12:30 utc)
    fun dailyExchangeRateSync() = syncExchange.executeForToday().also { evictCache() }

    @ExecuteAfterStart
    @Scheduled(cron = EVERY_FIRST_DAY_OF_MONTH)
    fun largeExchangeRatesSync() = syncExchange.executeForAllMissingDays().also { evictCache() }

    @ExecuteAfterStart
    @Scheduled(cron = EVERYDAY_AT_MIDNIGHT)
    fun dailyAverageSalarySync() = syncSalary.execute().also { evictCache() }

    @ExecuteAfterStart
    @Scheduled(cron = EVERYDAY_AT_MIDNIGHT)
    fun dailyUnemploymentRateSync() = syncUnemployment.execute().also { evictCache() }

    @ExecuteAfterStart
    @Scheduled(cron = EVERYDAY_AT_MIDNIGHT)
    fun dailyRealGdpSync() = syncRealGdp.execute().also { evictCache() }

    private fun evictCache() = evictCache.execute()


}