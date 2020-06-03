package com.estn.economy.core.domain

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service

@Service
class EvictAllCacheUseCase(private val cacheManager: CacheManager) {

    private val LOGGER = getLogger(this::class.java)

    fun execute() {
        cacheManager.cacheNames.map {
            cacheManager.getCache(it)
        }.forEach {
            it?.clear()
        }

        LOGGER.info("Evicted all cache")
    }

}