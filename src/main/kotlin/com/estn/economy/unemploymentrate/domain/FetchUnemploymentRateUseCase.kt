package com.estn.economy.unemploymentrate.domain

import com.estn.economy.unemploymentrate.data.database.UnemploymentRateRepository
import org.springframework.stereotype.Service

@Service
class FetchUnemploymentRateUseCase (private val repository: UnemploymentRateRepository) {

    fun fetchAllUnempRatesAveragedByYear() = repository.getAllYearlyAveragedUnemploymentRates()

}