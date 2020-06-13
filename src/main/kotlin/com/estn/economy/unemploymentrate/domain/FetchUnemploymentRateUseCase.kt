package com.estn.economy.unemploymentrate.domain

import com.estn.economy.unemploymentrate.data.database.UnemploymentRateRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class FetchUnemploymentRateUseCase(private val repository: UnemploymentRateRepository) {

    @Cacheable("FetchUnemploymentRateUseCase::fetchAllUnempRatesAveragedByYear")
    fun fetchAllUnempRatesAveragedByYear() = repository.getAllYearlyAveragedUnemploymentRates()

    @Cacheable("FetchUnemploymentRateUseCase::fetchAllMonthlyUnempRates")
    fun fetchAllMonthlyUnempRates() = repository.findAll()

}