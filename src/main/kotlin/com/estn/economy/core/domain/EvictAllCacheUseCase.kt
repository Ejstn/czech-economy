package com.estn.economy.core.domain

import com.estn.economy.exchangerate.domain.SynchronizeExchangeRateUseCase
import org.slf4j.LoggerFactory
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service

@Service
class EvictAllCacheUseCase(private val cacheManager: CacheManager) {

    private val LOGGER = LoggerFactory.getLogger(SynchronizeExchangeRateUseCase::class.java)

    fun execute() {
        cacheManager.cacheNames.map {
            cacheManager.getCache(it)
        }.forEach {
            it?.clear()
        }

        LOGGER.info("Evicted all cache")
    }

}