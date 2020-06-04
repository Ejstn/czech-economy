package com.estn.economy.grossdomesticproduct.domain

import com.estn.economy.core.domain.CalculateIncrementalValueChangesUseCase
import com.estn.economy.core.domain.InputData
import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductRepository
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class FetchGrossDomesticProductUseCase (private val repository: GrossDomesticProductRepository,
                                        private val calculateChanges: CalculateIncrementalValueChangesUseCase) {

    @Cacheable("FetchGrossDomesticProductUseCase::fetchGdp")
    fun fetchGdp(type: GrossDomesticProductType) = repository
            .getAllByTypeEquals(type)

    @Cacheable("FetchGrossDomesticProductUseCase::fetchPercentChangesPerYear")
    fun fetchPercentChangesPerYear(type: GrossDomesticProductType) : List<OutputPercentageData> {
        return repository.getAllSummedByYearHavingAllFourQuarters(type)
                .map { InputData(it.year.toLong(), it.gdpMillions) }
                .let { calculateChanges.calculatePercentageChanges(it)  }
    }

    @Cacheable("FetchGrossDomesticProductUseCase::fetchPercentChangesPerQuarter")
    fun fetchPercentChangesPerQuarter(type: GrossDomesticProductType) : List<OutputPercentageData> {
        return repository.getAllByTypeEquals(type)
                .mapIndexed { index, gdp -> Pair(index, gdp) }
                .map { InputData(it.first.toLong(), it.second.gdpMillionsCrowns) }
                .let { calculateChanges.calculatePercentageChanges(it)  }
    }






}