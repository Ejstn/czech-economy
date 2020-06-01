package com.estn.economy.core.domain

import com.estn.economy.exchangerate.domain.SynchronizeExchangeRateUseCase
import com.estn.economy.salary.domain.SynchronizeSalaryUseCase
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
                       private val evictCache: EvictAllCacheUseCase) {

    @Scheduled(fixedRateString = "\${synchronization.exchangerate.small.millis:60000}")
    fun smallRatesSync() = syncExchange.executeForToday().also { evictCache() }

    @Scheduled(fixedRateString = "\${synchronization.exchangerate.large.millis:60000}")
    fun largeRatesSync() = syncExchange.executeForAllMissingDays().also { evictCache() }

    @Scheduled(fixedRateString = "\${synchronization.average_salary.millis:60000}")
    fun syncSalary() = syncSalary.execute().also { evictCache() }

    private fun evictCache() = evictCache.execute()


}