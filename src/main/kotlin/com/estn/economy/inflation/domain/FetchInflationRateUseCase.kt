package com.estn.economy.inflation.domain

import com.estn.economy.inflation.data.InflationRateRepository
import com.estn.economy.inflation.data.InflationType
import org.springframework.stereotype.Service

@Service
class FetchInflationRateUseCase (private val repository: InflationRateRepository) {

    fun fetchAllYearlyInflationRates() =
            repository.findAllByTypeAndMonthEquals(InflationType.THIS_YEAR_VS_LAST_YEAR, 12)

}