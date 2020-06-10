package com.estn.economy.grossdomesticproduct.domain

import com.estn.economy.core.domain.CalculateIncrementalValueChangesUseCase
import com.estn.economy.core.domain.InputData
import com.estn.economy.core.domain.OutputPercentageData
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductEntity
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductRepository
import com.estn.economy.grossdomesticproduct.data.database.GrossDomesticProductType
import com.estn.economy.grossdomesticproduct.domain.model.GrossDomesticProductByYear
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class FetchGrossDomesticProductUseCase(private val repository: GrossDomesticProductRepository,
                                       private val calculateChanges: CalculateIncrementalValueChangesUseCase) {

    @Cacheable("FetchGrossDomesticProductUseCase::fetchGdp")
    fun fetchGdp(type: GrossDomesticProductType = GrossDomesticProductType.REAL_2010_PRICES) = repository
            .getAllByTypeEquals(type)

    @Cacheable("FetchGrossDomesticProductUseCase::fetchPercentChangesPerYear")
    fun fetchPercentChangesPerYear(type: GrossDomesticProductType = GrossDomesticProductType.REAL_2010_PRICES)
            : List<OutputPercentageData<GrossDomesticProductByYear>> {
        return repository.getAllSummedByYearHavingAllFourQuarters(type)
                .map { InputData(it.year.toLong(), it.gdpMillions, it) }
                .let { calculateChanges.calculatePercentageChanges(it) }
    }

    @Cacheable("FetchGrossDomesticProductUseCase::fetchPercentChangesPerQuarter")
    fun fetchPercentChangesPerQuarter(type: GrossDomesticProductType = GrossDomesticProductType.REAL_2010_PRICES)
            : List<OutputPercentageData<GrossDomesticProductEntity>> {
        return repository.getAllByTypeEquals(type)
                .mapIndexed { index, gdp -> Pair(index, gdp) }
                .map { InputData(it.first.toLong(), it.second.gdpMillionsCrowns, it.second) }
                .let { calculateChanges.calculatePercentageChanges(it) }
    }


}