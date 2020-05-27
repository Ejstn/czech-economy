package com.estn.economy.core.domain

import com.estn.economy.exchangerate.domain.SynchronizeExchangeRateUseCase
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

/**
 * Written by Martin Soukup on 14.1.2020.
 */
@Profile("!test")
@Component
class DataSynchronizer(private val synchronize: SynchronizeExchangeRateUseCase,
                       private val evictCache: EvictAllCacheUseCase) {

    @Scheduled(fixedRateString = "\${synchronization.exchangerate.small.millis:60000}")
    fun smallRatesSync() {
        synchronize.executeForToday()
        evictCache()
    }

    @Scheduled(fixedRateString = "\${synchronization.exchangerate.large.millis:60000}")
    fun largeRatesSync() {
        synchronize.executeForAllMissingDays()
        evictCache()
    }

    private fun evictCache() = evictCache.execute()

}